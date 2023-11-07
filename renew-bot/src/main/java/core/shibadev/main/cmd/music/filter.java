package core.shibadev.main.cmd.music;

import core.shibadev.main.Main;
import core.shibadev.main.lavalink.KmPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class filter {

    public filter(SlashCommandInteractionEvent event, InteractionHook a) {

        KmPlayer Player = Main.Manager.getPlayer(event.getGuild());
        if (Player == null) {
            Player = new KmPlayer(Main.Manager, event.getGuild(), event.getMember().getVoiceState().getChannel().getId(), event.getTextChannel().getId());
        }
        System.out.println("Filter Test "+event.getOption("fixed_filter").getAsString());
        EmbedBuilder embedBuilder =new EmbedBuilder();
        embedBuilder.setTitle("Fixed filter set to "+event.getOption("fixed_filter").getAsString());
        embedBuilder.setColor(Color.blue);
        embedBuilder.setTimestamp(event.getTimeCreated());
        a.editOriginalEmbeds(embedBuilder.build()).queue();
        switch (event.getOption("fixed_filter").getAsString()){
            case "Bass Mode" -> Player.filter_bass();
            case "Soft Mode" -> Player.filter_soft();
            case "Karaoke Mode" -> Player.filter_karaoke();
            case "Slowmotion Mode" -> Player.filter_slowmotion();
            case "Vaporwave Mode" -> Player.filter_vaporwave();
            case "NightCore Mode" -> Player.filter_nightcore();
            case "clear filter"   -> Player.filter_clear();
        }
    }
}
