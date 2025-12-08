package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Maintenance;
import org.example.client.model.Aircraft;
import org.example.client.util.HttpClientUtil;
import org.json.JSONObject;

import java.util.List;

public class MaintenanceApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/maintenance";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Maintenance> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        checkApiError(json);
        List<Maintenance> list = mapper.readValue(json, new TypeReference<List<Maintenance>>() {});
        for (Maintenance m : list) {
            enrichAircraft(m);
        }
        return list;
    }

    public static Maintenance save(Maintenance m) throws Exception {
        validateMaintenance(m);
        String json = HttpClientUtil.sendPost(BASE_URL, mapper.writeValueAsString(m));
        checkApiError(json);
        Maintenance saved = mapper.readValue(json, Maintenance.class);
        enrichAircraft(saved);
        return saved;
    }

    public static Maintenance update(long id, Maintenance m) throws Exception {
        validateMaintenance(m);
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id, mapper.writeValueAsString(m));
        checkApiError(json);
        Maintenance updated = mapper.readValue(json, Maintenance.class);
        enrichAircraft(updated);
        return updated;
    }

    public static void delete(long id) throws Exception {
        String json = HttpClientUtil.sendDelete(BASE_URL + "/" + id);
        checkApiError(json);
    }

    private static void enrichAircraft(Maintenance m) {
        try {
            Aircraft a = m.getAircraft();
            if (a != null && a.getAircraftCode() != null && !a.getAircraftCode().isBlank()) {
                Aircraft full = AircraftApi.getByCode(a.getAircraftCode());
                m.setAircraft(full);
            }
        } catch (Exception ignore) {}
    }

    private static void validateMaintenance(Maintenance m) {
        if (m == null) throw new IllegalArgumentException("Maintenance не может быть null");
        Aircraft a = m.getAircraft();
        if (a == null || a.getAircraftCode() == null || a.getAircraftCode().isBlank()) {
            throw new IllegalArgumentException("Самолёт для обслуживания не выбран");
        }
        if (m.getMaintenanceDate() == null) throw new IllegalArgumentException("Дата обслуживания не может быть пустой");
        if (m.getType() == null || m.getType().isBlank()) throw new IllegalArgumentException("Тип обслуживания не может быть пустым");
    }

    private static void checkApiError(String json) {
        if (json == null || json.trim().isEmpty()) return;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has("message")) throw new RuntimeException(obj.getString("message"));
        } catch (org.json.JSONException ignore) {}
    }
}
