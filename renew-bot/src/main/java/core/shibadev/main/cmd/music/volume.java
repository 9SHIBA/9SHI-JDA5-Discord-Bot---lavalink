package core.shibadev.main.cmd.music;

import core.shibadev.main.Main;
import core.shibadev.main.lavalink.KmPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class volume {
    public volume(SlashCommandInteractionEvent event, InteractionHook a){
        if (event.getMember() != null && event.getMember().getVoiceState() != null && event.getMember().getVoiceState().getChannel() != null) {
            KmPlayer Player = Main.Manager.getPlayer(event.getGuild());
            if (Player == null) {
                Player = new KmPlayer(Main.Manager, event.getGuild(), event.getMember().getVoiceState().getChannel().getId(), event.getTextChannel().getId());
            }
            int volume =  event.getOption("volume").getAsInt();
            if(volume <= 100){
            Player.volume(volume);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Volume set : "+ volume + "%");
                embed.addField("Set by","<@"+event.getMember().getId()+">",true);
                embed.addField("Command at","<#"+event.getGuildChannel().getId()+">",true);
                embed.addField("Voice channel","<#"+event.getMember().getVoiceState().getChannel().getId()+">",true);
                embed.setColor(Color.blue);
                embed.setTimestamp(event.getTimeCreated());
                a.editOriginalEmbeds(embed.build()).queue();
            }else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Error . . . insert number 1 .. 100");
                builder.setImage("https://c.tenor.com/cw9FteFI798AAAAM/miku-meme.gif");
                builder.setColor(Color.blue);
                builder.setTimestamp(event.getTimeCreated());
                a.editOriginalEmbeds(builder.build()).queue();

            }
        }
    }
}
