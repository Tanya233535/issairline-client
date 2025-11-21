package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Aircraft;
import org.example.client.util.HttpClientUtil;

import java.util.List;

public class AircraftApi {

    private static final String BASE_URL = "http://localhost:8080/api/aircrafts";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Aircraft> getAll() throws Exception {
        String json = HttpClientUtil.get(BASE_URL);
        return mapper.readValue(json, new TypeReference<List<Aircraft>>() {});
    }

    public static Aircraft create(Aircraft aircraft) throws Exception {
        String json = HttpClientUtil.post(BASE_URL, mapper.writeValueAsString(aircraft));
        return mapper.readValue(json, Aircraft.class);
    }

    public static Aircraft update(Aircraft aircraft) throws Exception {
        String json = HttpClientUtil.put(BASE_URL + "/" + aircraft.getAircraftCode(),
                mapper.writeValueAsString(aircraft));
        return mapper.readValue(json, Aircraft.class);
    }

    public static void delete(String code) throws Exception {
        HttpClientUtil.delete(BASE_URL + "/" + code);
    }
}
