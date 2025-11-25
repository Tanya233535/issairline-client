package org.example.client.api;

import org.example.client.model.Flight;
import org.example.client.util.HttpClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class FlightApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/flights";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Flight> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        return mapper.readValue(json, new TypeReference<List<Flight>>() {});
    }

    public static Flight save(Flight f) throws Exception {
        String json = HttpClientUtil.sendPost(BASE_URL,
                mapper.writeValueAsString(f));
        return mapper.readValue(json, Flight.class);
    }

    public static Flight update(long id, Flight f) throws Exception {
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id,
                mapper.writeValueAsString(f));
        return mapper.readValue(json, Flight.class);
    }

    public static void delete(long id) throws Exception {
        HttpClientUtil.sendDelete(BASE_URL + "/" + id);
    }
}
