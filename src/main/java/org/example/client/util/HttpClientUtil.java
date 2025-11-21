package org.example.client.util;

import java.net.http.*;
import java.net.URI;

public class HttpClientUtil {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String get(String url) throws Exception {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String post(String url, String json) throws Exception {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json").build();
        return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String put(String url, String json) throws Exception {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json").build();
        return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static void delete(String url) throws Exception {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url))
                .DELETE().build();
        client.send(req, HttpResponse.BodyHandlers.ofString());
    }
}
