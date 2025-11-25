package org.example.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.model.CrewMember;
import org.example.client.util.HttpClientUtil;

import java.util.List;

public class CrewApi {

    private static final String BASE_URL = "http://localhost:8080/iss/api/crew";
    private static final ObjectMapper mapper = HttpClientUtil.mapper;

    public static List<CrewMember> getAll() throws Exception {
        String json = HttpClientUtil.sendGet(BASE_URL);
        return mapper.readValue(json, new TypeReference<List<CrewMember>>() {});
    }

    public static CrewMember save(CrewMember c) throws Exception {
        String json = HttpClientUtil.sendPost(BASE_URL,
                mapper.writeValueAsString(c));
        return mapper.readValue(json, CrewMember.class);
    }

    public static CrewMember update(long id, CrewMember c) throws Exception {
        String json = HttpClientUtil.sendPut(BASE_URL + "/" + id,
                mapper.writeValueAsString(c));
        return mapper.readValue(json, CrewMember.class);
    }

    public static void delete(long id) throws Exception {
        HttpClientUtil.sendDelete(BASE_URL + "/" + id);
    }

}
