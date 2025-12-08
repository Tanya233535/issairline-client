package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.Aircraft;
import org.example.client.model.Flight;
import org.example.client.util.HttpClientUtil;
import org.json.JSONObject;

import java.util.List;

public class FlightApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/flights";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<Flight> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        checkApiError(json);
        List<Flight> flights = mapper.readValue(json, new TypeReference<List<Flight>>() {});
        for (Flight f : flights) {
            enrichAircraft(f);
        }
        return flights;
    }

    public static Flight getById(long id) throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL + "/" + id);
        checkApiError(json);
        Flight f = mapper.readValue(json, Flight.class);
        enrichAircraft(f);
        return f;
    }

    public static Flight save(Flight f) throws Exception {
        validateFlight(f);
        String json = HttpClientUtil.sendPost(BASE_URL, mapper.writeValueAsString(f));
        checkApiError(json);
        Flight saved = mapper.readValue(json, Flight.class);
        enrichAircraft(saved);
        return saved;
    }

    public static Flight update(long id, Flight f) throws Exception {
        validateFlight(f);
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id, mapper.writeValueAsString(f));
        checkApiError(json);
        Flight updated = mapper.readValue(json, Flight.class);
        enrichAircraft(updated);
        return updated;
    }

    public static void delete(long id) throws Exception {
        String json = HttpClientUtil.sendDelete(BASE_URL + "/" + id);
        checkApiError(json);
    }

    private static void enrichAircraft(Flight f) {
        try {
            Aircraft a = f.getAircraft();
            if (a != null && a.getAircraftCode() != null && !a.getAircraftCode().isBlank()) {
                Aircraft full = AircraftApi.getByCode(a.getAircraftCode());
                f.setAircraft(full);
            }
        } catch (Exception ignore) {}
    }

    private static void validateFlight(Flight f) {
        if (f.getFlightNo() == null || f.getFlightNo().isBlank()) {
            throw new IllegalArgumentException("Номер рейса не может быть пустым");
        }
        if (f.getDepartureAirport() == null || f.getDepartureAirport().isBlank()) {
            throw new IllegalArgumentException("Аэропорт вылета не может быть пустым");
        }
        if (f.getArrivalAirport() == null || f.getArrivalAirport().isBlank()) {
            throw new IllegalArgumentException("Аэропорт прибытия не может быть пустым");
        }
        if (f.getScheduledDeparture() == null) {
            throw new IllegalArgumentException("Время вылета не может быть пустым");
        }
        if (f.getScheduledArrival() == null) {
            throw new IllegalArgumentException("Время прилёта не может быть пустым");
        }
        if (f.getAircraft() == null || f.getAircraft().getAircraftCode() == null || f.getAircraft().getAircraftCode().isBlank()) {
            throw new IllegalArgumentException("Самолёт не выбран или указан некорректно");
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
