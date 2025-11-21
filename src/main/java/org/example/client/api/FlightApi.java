package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Aircraft;
import org.example.client.model.Flight;
import org.example.client.util.HttpClientUtil;

import java.net.http.HttpResponse;
import java.util.List;

public class FlightApi {

    private static final String URL = "http://localhost:8080/api/flights";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Flight> getAll() throws Exception {
        HttpResponse<String> response = HttpClientUtil.get(URL);

        return mapper.readValue(response.body(), new TypeReference<List<Flight>>() {});
    }

    public static Flight getOne(long id) throws Exception {
        HttpResponse<String> response = HttpClientUtil.get(URL + "/" + id);

        return mapper.readValue(response.body(), Flight.class);
    }

    public static Flight save(Flight f) throws Exception {
        HttpResponse<String> response = HttpClientUtil.post(URL, mapper.writeValueAsString(f));
        return mapper.readValue(response.body(), Flight.class);
    }

    public static Flight update(long id, Flight f) throws Exception {
        HttpResponse<String> response = HttpClientUtil.put(URL + "/" + id, mapper.writeValueAsString(f));
        return mapper.readValue(response.body(), Flight.class);
    }

    public static void delete(long id) throws Exception {
        HttpClientUtil.delete(URL + "/" + id);
    }
}
