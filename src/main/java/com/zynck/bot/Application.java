package com.zynck.bot;

import com.zynck.bot.model.Bot;
import com.zynck.bot.task.CurrencyTask;
import com.zynck.bot.task.RiotTask;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.constant.Platform;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.time.Duration;
import java.util.Objects;
import java.util.Timer;

public class Application {

    public static final String RIOT_API_KEY = System.getenv("RIOT_API_KEY");

    @Getter(AccessLevel.PUBLIC)
    private static final Bot bot = Bot.builder()
            .token(System.getenv("BOT_TOKEN"))
            .build();

    @Getter(AccessLevel.PUBLIC)
    private static RiotApi riotApi;

    @SneakyThrows
    public static void main(String[] args) {
        var config = new ApiConfig().setKey(System.getenv("RIOT_API_KEY"));
        riotApi = new RiotApi(config);

        bot.start();

        var timer = new Timer();
        timer.schedule(new CurrencyTask(), 0, Duration.ofMinutes(3).toMillis());
        timer.schedule(new RiotTask(), 0, Duration.ofMinutes(5).toMillis());
    }
}
