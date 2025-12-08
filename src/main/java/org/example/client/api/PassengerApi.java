package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Flight;
import org.example.client.model.Passenger;
import org.example.client.util.HttpClientUtil;
import org.json.JSONObject;

import java.util.List;

public class PassengerApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/passengers";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Passenger> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        checkApiError(json);
        List<Passenger> list = mapper.readValue(json, new TypeReference<List<Passenger>>() {});
        for (Passenger p : list) {
            enrichFlight(p);
        }
        return list;
    }

    public static Passenger create(Passenger p) throws Exception {
        validatePassenger(p);
        String json = HttpClientUtil.sendPost(BASE_URL, mapper.writeValueAsString(p));
        checkApiError(json);
        Passenger saved = mapper.readValue(json, Passenger.class);
        enrichFlight(saved);
        return saved;
    }

    public static Passenger update(long id, Passenger p) throws Exception {
        validatePassenger(p);
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id, mapper.writeValueAsString(p));
        checkApiError(json);
        Passenger updated = mapper.readValue(json, Passenger.class);
        enrichFlight(updated);
        return updated;
    }

    public static void delete(long id) throws Exception {
        String json = HttpClientUtil.sendDelete(BASE_URL + "/" + id);
        checkApiError(json);
    }

    private static void enrichFlight(Passenger p) {
        try {
            Flight f = p.getFlight();
            if (f != null && f.getId() > 0) {
                Flight full = FlightApi.getById(f.getId());
                p.setFlight(full);
            }
        } catch (Exception ignore) {}
    }

    private static void validatePassenger(Passenger p) {
        if (p == null) throw new IllegalArgumentException("Passenger не может быть null");
        if (p.getFirstName() == null || p.getFirstName().isBlank()) throw new IllegalArgumentException("Имя пассажира не может быть пустым");
        if (p.getLastName() == null || p.getLastName().isBlank()) throw new IllegalArgumentException("Фамилия пассажира не может быть пустой");
        Flight f = p.getFlight();
        if (f == null || f.getId() <= 0) throw new IllegalArgumentException("Рейс для пассажира не выбран");
    }

    private static void checkApiError(String json) {
        if (json == null || json.trim().isEmpty()) return;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has("message")) throw new RuntimeException(obj.getString("message"));
        } catch (org.json.JSONException ignore) {}
    }
}
