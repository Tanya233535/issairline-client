package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.CrewMember;
import org.example.client.model.Flight;
import org.example.client.util.HttpClientUtil;
import org.json.JSONObject;

import java.util.List;

public class CrewApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/crew";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<CrewMember> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        checkApiError(json);
        List<CrewMember> list = mapper.readValue(json, new TypeReference<List<CrewMember>>() {});
        for (CrewMember c : list) {
            enrichFlight(c);
        }
        return list;
    }

    public static CrewMember save(CrewMember c) throws Exception {
        validateCrewMember(c);
        String json = HttpClientUtil.sendPost(BASE_URL, mapper.writeValueAsString(c));
        checkApiError(json);
        CrewMember saved = mapper.readValue(json, CrewMember.class);
        enrichFlight(saved);
        return saved;
    }

    public static CrewMember update(long id, CrewMember c) throws Exception {
        validateCrewMember(c);
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id, mapper.writeValueAsString(c));
        checkApiError(json);
        CrewMember updated = mapper.readValue(json, CrewMember.class);
        enrichFlight(updated);
        return updated;
    }

    public static void delete(long id) throws Exception {
        String json = HttpClientUtil.sendDelete(BASE_URL + "/" + id);
        checkApiError(json);
    }

    private static void enrichFlight(CrewMember c) {
        try {
            Flight f = c.getFlight();
            if (f != null && f.getId() > 0) {
                Flight full = FlightApi.getById(f.getId());
                c.setFlight(full);
            }
        } catch (Exception ignore) {}
    }

    private static void validateCrewMember(CrewMember c) {
        if (c == null) throw new IllegalArgumentException("CrewMember не может быть null");
        if (c.getFirstName() == null || c.getFirstName().isBlank()) throw new IllegalArgumentException("Имя члена экипажа не может быть пустым");
        if (c.getLastName() == null || c.getLastName().isBlank()) throw new IllegalArgumentException("Фамилия члена экипажа не может быть пустой");
        if (c.getRole() == null || c.getRole().isBlank()) throw new IllegalArgumentException("Роль члена экипажа не может быть пустой");
        Flight f = c.getFlight();
        if (f == null || f.getId() <= 0) throw new IllegalArgumentException("Рейс для члена экипажа не выбран");
    }

    private static void checkApiError(String json) {
        if (json == null || json.trim().isEmpty()) return;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has("message")) throw new RuntimeException(obj.getString("message"));
        } catch (org.json.JSONException ignore) {}
    }
}
