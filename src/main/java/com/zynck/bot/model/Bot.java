package com.zynck.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

@Data
@AllArgsConstructor
@Builder
public class Bot {

    private String token;
    private JDA jda;

    @SneakyThrows
    public void start() {
        this.jda = JDABuilder.createDefault(this.token).build()
                .awaitReady();
    }


}
