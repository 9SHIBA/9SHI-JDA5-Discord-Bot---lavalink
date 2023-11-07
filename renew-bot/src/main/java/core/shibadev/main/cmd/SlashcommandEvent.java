package core.shibadev.main.cmd;

import core.shibadev.main.Main;
import core.shibadev.main.cmd.addon.status;
import core.shibadev.main.cmd.music.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SlashcommandEvent extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        Main.Manager.init(event.getJDA());
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null){
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Loading Data...");
        embedBuilder.setTimestamp(event.getTimeCreated());
        embedBuilder.setColor(Color.blue);
        embedBuilder.setImage("https://c.tenor.com/Uebm7fkQJ18AAAAd/hatsune-miku-miku.gif");
        event.replyEmbeds(embedBuilder.build()).queue(a -> {
            switch (event.getName()) {
                case "play" -> new play(event,a);
                case "stop" -> new stop(event,a);
                case "skip" -> new skip(event,a);
                case "filter" -> new filter(event,a);
                case "volume" -> new volume(event,a);
                case "pause" -> new pause(event,a);
                case "bassboost" -> new bassboost(event,a);
                case "loop" -> new loop(event,a);
                case "status" -> {
                    try {
                        new status(event,a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
}
