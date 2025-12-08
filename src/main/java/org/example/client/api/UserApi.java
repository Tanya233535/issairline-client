package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.User;
import org.example.client.util.HttpClientUtil;
import org.json.JSONObject;

import java.util.List;

public class UserApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/users";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<User> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        checkApiError(json);
        return mapper.readValue(json, new TypeReference<List<User>>() {});
    }

    public static User create(User u) throws Exception {
        String json = HttpClientUtil.sendPost(BASE_URL, mapper.writeValueAsString(u));
        checkApiError(json);
        return mapper.readValue(json, User.class);
    }

    public static User update(long id, User u) throws Exception {
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id, mapper.writeValueAsString(u));
        checkApiError(json);
        return mapper.readValue(json, User.class);
    }

    public static void delete(long id) throws Exception {
        String json = HttpClientUtil.sendDelete(BASE_URL + "/" + id);
        checkApiError(json);
    }

    private static void checkApiError(String json) {
        if (json == null || json.trim().isEmpty()) return;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has("message")) throw new RuntimeException(obj.getString("message"));
        } catch (org.json.JSONException ignore) {}
    }

    public static int getCount() throws Exception {
        String json = HttpClientUtil.sendGet("http://localhost:8080/iss/api/stats/users/count");

        if (json == null || json.trim().isEmpty()) return 0;

        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has("message")) throw new RuntimeException(obj.getString("message"));
        } catch (org.json.JSONException ignore) {
        }

        try {
            return Integer.parseInt(json.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Ошибка преобразования количества пользователей: " + json, e);
        }
    }
}
