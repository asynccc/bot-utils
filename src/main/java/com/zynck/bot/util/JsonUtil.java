package com.zynck.bot.util;

import com.zynck.bot.Application;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonUtil {

    @SneakyThrows
    public static JSONObject getResponse(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?api_key=" + Application.RIOT_API_KEY))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body());
    }

}
