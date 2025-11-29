package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Passenger;
import org.example.client.util.HttpClientUtil;
import org.json.JSONObject;

import java.util.List;

public class PassengerApi {

    private static final String BASE = "http://localhost:8080/iss/api/passengers";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Passenger> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE);

        checkApiError(json);

        return mapper.readValue(json, new TypeReference<List<Passenger>>() {});
    }

    public static Passenger create(Passenger p) throws Exception {
        String json = HttpClientUtil.sendPost(
                BASE,
                mapper.writeValueAsString(p)
        );

        checkApiError(json);

        return mapper.readValue(json, Passenger.class);
    }

    public static Passenger update(long id, Passenger p) throws Exception {
        String json = HttpClientUtil.sendPut(
                BASE + "/" + id,
                mapper.writeValueAsString(p)
        );

        checkApiError(json);

        return mapper.readValue(json, Passenger.class);
    }

    public static void delete(long id) throws Exception {
        String json = HttpClientUtil.sendDelete(BASE + "/" + id);

        checkApiError(json);
    }

    private static void checkApiError(String json) {
        if (json == null || json.trim().isEmpty()) return;

        try {
            JSONObject o = new JSONObject(json);

            if (o.has("message")) {
                throw new RuntimeException(o.getString("message"));
            }

        } catch (org.json.JSONException ignore) {
        }
    }
}
