package com.zynck.bot.task;

import com.zynck.bot.Application;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.constant.Platform;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimerTask;

public class RiotTask extends TimerTask {

    private static final String[] messages = {
      "Eu vou conseguir, eu sei disso - Kléberson Bixeiro",
      "Vou pegar diamond, só pra snipar esse gordo fodido - Kléberson Caloteiro",
      "Mestre é logo ali otário, relaxa - Kléberson Fabinhas",
      "Sempre esperei dele o melhor - Proplayer, Godoxa",
      "Só tenho a agradecer todos os meus amigos - Kendersi",
    };

    @SneakyThrows
    @Override
    public void run() {
        var api = Application.getRiotApi();

        var summoner = api.getSummonerByName(Platform.BR, "KleboPIRIGOSO");
        var league = api.getLeagueEntriesBySummonerId(Platform.BR, summoner.getId());

        var rank = new StringBuilder();
        var gotIt = new StringBuilder();

        league.forEach(leagueEntry -> {
            gotIt.append("Ele está no **").append(StringUtils.capitalize(leagueEntry.getTier().toLowerCase())).append(" ").append(leagueEntry.getRank()).append("**\n");
            gotIt.append("Seu objetivo é: **Diamond IV**\n");
            gotIt.append("Ele consegiu? ").append((leagueEntry.getLeaguePoints() < 100) ? "**Não, faltam " + (100 - leagueEntry.getLeaguePoints()) + " pontos**" : "**Sim!!!**").append("\n");

            var games = leagueEntry.getWins() + leagueEntry.getLosses();
            var percentage = (leagueEntry.getWins() * 100) / games;

            rank.append("<:platinum:958518249279082546> ").append(StringUtils.capitalize(leagueEntry.getTier().toLowerCase())).append(" ").append(leagueEntry.getRank()).append("\n")
                    .append("**").append(leagueEntry.getLeaguePoints()).append("PDL │ ** **")
                    .append(leagueEntry.getWins()).append("W/")
                    .append(leagueEntry.getLosses()).append("L**")
                    .append("\n Winrate: **").append(percentage).append("%**");
        });

        var timestamp = new Timestamp(Instant.now().toEpochMilli());
        var data = new Date(timestamp.getTime());
        var date = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
        var hourFormat = new SimpleDateFormat("HH:mm");

        var embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        embed.setTitle("Olá a todos, compartilhando jornada de KleboPIRIGOSO");
        embed.setDescription("Será que o nosso guerreiro extraordinário está perto de pegar diamante?\n\n Esperamos que ele tenha conseguido!");
        embed.addBlankField(false);
        embed.setThumbnail("https://ddragon.leagueoflegends.com/cdn/12.3.1/img/profileicon/" + summoner.getProfileIconId() + ".png");
        embed.addField(":grinning: Ele conseguiu?", gotIt.toString(), false);
        embed.addBlankField(false);
        embed.addField(":mega: Informações", "Nível: **" + summoner.getSummonerLevel() + "**\nÚltima atualização: **" + date.format(data) + " - " + hourFormat.format(date),true);
        embed.addField(":video_game: Últimos jogos", "**0G**",true);
        embed.addField(":mag: Status pessoal", rank.toString(),true);
        embed.setFooter(messages[RandomUtils.nextInt(0, messages.length - 1)]);

        Objects.requireNonNull(Application.getBot().getJda().getTextChannelById("957368092240007168")).editMessageEmbedsById("957368160707825674", embed.build()).queue();
    }
}
