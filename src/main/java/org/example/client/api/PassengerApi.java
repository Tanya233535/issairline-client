package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Passenger;
import org.example.client.util.HttpClientUtil;

import java.util.List;

public class PassengerApi {

    private static final String BASE = "http://localhost:8080/iss/api/passengers";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Passenger> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE);
        return mapper.readValue(json, new TypeReference<List<Passenger>>() {});
    }

    public static Passenger create(Passenger p) throws Exception {
        String json = HttpClientUtil.sendPost(BASE, mapper.writeValueAsString(p));
        return mapper.readValue(json, Passenger.class);
    }

    public static Passenger update(long id, Passenger p) throws Exception {
        String json = HttpClientUtil.sendPut(BASE + "/" + id,
                mapper.writeValueAsString(p));
        return mapper.readValue(json, Passenger.class);
    }

    public static void delete(long id) throws Exception {
        HttpClientUtil.sendDelete(BASE + "/" + id);
    }
}
