package org.example.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.*;
import java.util.Base64;

public class HttpClientUtil {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static String authHeader = null;

    public static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void setCredentials(String username, String password) {
        String token = username + ":" + password;
        authHeader = "Basic " + Base64.getEncoder().encodeToString(token.getBytes());
    }

    private static HttpRequest.Builder base(String url) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(new URI(url));

        if (authHeader != null)
            builder.header("Authorization", authHeader);

        return builder;
    }

    public static String sendGet(String url) throws Exception {
        HttpRequest request = base(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String sendPost(String url, String json) throws Exception {
        HttpRequest request = base(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String sendPut(String url, String json) throws Exception {
        HttpRequest request = base(url)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String sendDelete(String url) throws Exception {
        HttpRequest request = base(url).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
