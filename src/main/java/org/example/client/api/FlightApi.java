package org.example.client.api;

import org.example.client.model.Flight;
import org.example.client.util.HttpClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONObject;

import java.util.List;

public class FlightApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/flights";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Flight> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);

        checkApiError(json);

        return mapper.readValue(json, new TypeReference<List<Flight>>() {});
    }

    public static Flight save(Flight f) throws Exception {
        String json = HttpClientUtil.sendPost(
                BASE_URL,
                mapper.writeValueAsString(f)
        );

        checkApiError(json);

        return mapper.readValue(json, Flight.class);
    }

    public static Flight update(long id, Flight f) throws Exception {
        String json = HttpClientUtil.sendPut(
                BASE_URL + "/" + id,
                mapper.writeValueAsString(f)
        );

        checkApiError(json);

        return mapper.readValue(json, Flight.class);
    }

    public static void delete(long id) throws Exception {
        String json = HttpClientUtil.sendDelete(BASE_URL + "/" + id);

        checkApiError(json);
    }

    private static void checkApiError(String json) {

        if (json == null || json.trim().isEmpty()) return;

        try {
            JSONObject obj = new JSONObject(json);

            if (obj.has("message")) {
                String msg = obj.getString("message");
                throw new RuntimeException(msg);
            }

        } catch (org.json.JSONException ignore) {
        }
    }
}
