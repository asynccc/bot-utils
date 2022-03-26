package com.zynck.bot;

import com.zynck.bot.model.Bot;
import com.zynck.bot.task.CurrencyTask;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.Duration;
import java.util.Timer;

public class Application {

    @Getter(AccessLevel.PUBLIC)
    private static final Bot bot = Bot.builder()
            .token(System.getenv("BOT_TOKEN"))
            .build();

    public static void main(String[] args) {
        bot.start();

        var timer = new Timer();
        timer.schedule(new CurrencyTask(), 0, Duration.ofMinutes(3).toMillis());
    }
}
