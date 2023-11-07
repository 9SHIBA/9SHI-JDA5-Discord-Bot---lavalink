package core.shibadev.main.cmd.music;

import core.shibadev.main.Main;
import core.shibadev.main.lavalink.KmPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class bassboost {
    public bassboost(SlashCommandInteractionEvent event, InteractionHook a){
        if (event.getMember() != null && event.getMember().getVoiceState() != null && event.getMember().getVoiceState().getChannel() != null) {
            KmPlayer Player = Main.Manager.getPlayer(event.getGuild());
            if (Player == null) {
                Player = new KmPlayer(Main.Manager, event.getGuild(), event.getMember().getVoiceState().getChannel().getId(), event.getTextChannel().getId());
            }
            int per = event.getOption("bassboost").getAsInt();

            if(per < 100) {
                return;
            }
    try {
        Player.bassboost(per);
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Enable bass boost : "+ per +"%");
        b.setColor(Color.blue);
        b.setTimestamp(event.getTimeCreated());
        a.editOriginalEmbeds(b.build()).queue();
    }catch (Exception e){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Error . . . Some thing went wrong try again");
        builder.setImage("https://c.tenor.com/cw9FteFI798AAAAM/miku-meme.gif");
        builder.setColor(Color.blue);
        builder.setTimestamp(event.getTimeCreated());
        a.editOriginalEmbeds(builder.build()).queue();
                        }

        }
    }
}
