package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Aircraft;
import org.example.client.util.HttpClientUtil;
import org.json.JSONObject;

import java.util.List;

public class AircraftApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/aircrafts";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Aircraft> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        checkApiError(json);
        return mapper.readValue(json, new TypeReference<List<Aircraft>>() {});
    }

    public static Aircraft getByCode(String code) throws Exception {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Aircraft code не может быть пустым");
        String json = HttpClientUtil.sendGet(BASE_URL + "/" + code);
        checkApiError(json);
        return mapper.readValue(json, Aircraft.class);
    }

    public static Aircraft create(Aircraft a) throws Exception {
        validateAircraft(a);
        String json = HttpClientUtil.sendPost(BASE_URL, mapper.writeValueAsString(a));
        checkApiError(json);
        return mapper.readValue(json, Aircraft.class);
    }

    public static Aircraft update(Aircraft a) throws Exception {
        validateAircraft(a);
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + a.getAircraftCode(), mapper.writeValueAsString(a));
        checkApiError(json);
        return mapper.readValue(json, Aircraft.class);
    }

    public static void delete(String code) throws Exception {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Aircraft code не может быть пустым");
        String json = HttpClientUtil.sendDelete(BASE_URL + "/" + code);
        checkApiError(json);
    }

    private static void validateAircraft(Aircraft a) {
        if (a == null) throw new IllegalArgumentException("Aircraft не может быть null");
        if (a.getAircraftCode() == null || a.getAircraftCode().isBlank()) {
            throw new IllegalArgumentException("Aircraft code не может быть пустым");
        }
        if (a.getModel() == null || a.getModel().isBlank()) {
            throw new IllegalArgumentException("Модель самолёта не может быть пустой");
        }
    }

    private static void checkApiError(String json) {
        if (json == null || json.trim().isEmpty()) return;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has("message")) throw new RuntimeException(obj.getString("message"));
        } catch (org.json.JSONException ignore) {}
    }
}
