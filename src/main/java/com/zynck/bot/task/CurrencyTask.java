package com.zynck.bot.task;

import com.zynck.bot.Application;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.TimerTask;

public class CurrencyTask extends TimerTask {

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Mostrando cotação do dólar");

        var currency = "USD-BRL";
        var response = getCurrencyResponse(currency);
        var object = response.getJSONObject(currency.replace("-", ""));

        var name = getField(object, "name").replace("/", " para ");

        var high = Double.parseDouble(getField(object, "high"));
        var highPrice = NumberFormat.getCurrencyInstance().format(high);

        var low = Double.parseDouble(getField(object, "low"));
        var lowPrice = NumberFormat.getCurrencyInstance().format(low);

        var jda = Application.getBot().getJda();
        var channel = Objects.requireNonNull(jda.getGuildById("685279467555389511")).getTextChannelById("957328140366123099");

        var emote = high > low ? ":grinning: :chart_with_upwards_trend: " : ":sob: :chart_with_downwards_trend: ";

        var embed = new EmbedBuilder();

        embed.setTitle("Atenção! Notificando todos os bixeiros!");
        embed.setDescription("Mostrando informações sobre a cotação do dólar.");
        embed.setColor(Color.GREEN);
        embed.addField(emote + " Precificação", ":moneybag: **(Atual)** Preço mais baixo **" + lowPrice + "**" + "\n" + "Preço mais alto **" + highPrice + "**", true);
        embed.addField(":earth_americas: Conversão de Moeda ", name, true);

        channel.editMessageEmbedsById("957336619483004958", embed.build()).queue();
    }

    private JSONObject getCurrencyResponse(String currency) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                        URI.create("https://economia.awesomeapi.com.br/json/last/" + currency)
                ).header("Content-Type", "application/json").
                build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }

    private String getField(JSONObject object, String field) {
        return object.getString(field);
    }
}
