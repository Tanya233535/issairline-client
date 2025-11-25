package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Maintenance;
import org.example.client.util.HttpClientUtil;

import java.util.List;

public class MaintenanceApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/maintenance";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Maintenance> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        return mapper.readValue(json, new TypeReference<List<Maintenance>>() {});
    }

    public static Maintenance save(Maintenance m) throws Exception {
        String json = HttpClientUtil.sendPost(BASE_URL,
                mapper.writeValueAsString(m));
        return mapper.readValue(json, Maintenance.class);
    }

    public static Maintenance update(long id, Maintenance m) throws Exception {
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id,
                mapper.writeValueAsString(m));
        return mapper.readValue(json, Maintenance.class);
    }

    public static void delete(long id) throws Exception {
        HttpClientUtil.sendDelete(BASE_URL + "/" + id);
    }
}
