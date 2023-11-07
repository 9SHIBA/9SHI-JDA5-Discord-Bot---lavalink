package core.shibadev.main.cmd.music;

import core.shibadev.main.Main;
import core.shibadev.main.lavalink.KmPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;

public class loop {
    public loop(SlashCommandInteractionEvent event, InteractionHook a) {
        if (event.getMember() != null && event.getMember().getVoiceState() != null && event.getMember().getVoiceState().getChannel() != null) {
            KmPlayer Player = Main.Manager.getPlayer(event.getGuild());
            if (Player == null) {
                Player = new KmPlayer(Main.Manager, event.getGuild(), event.getMember().getVoiceState().getChannel().getId(), event.getTextChannel().getId());
            }
            if (Player.CONNECTED){

        if(Player.getLoop() == false){
            Player.setLoop(true);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Loop Music");
            embed.addField("Loop by","<@"+event.getMember().getId()+">",true);
            embed.addField("Command at","<#"+event.getGuildChannel().getId()+">",true);
            embed.addField("Voice channel","<#"+event.getMember().getVoiceState().getChannel().getId()+">",true);
            embed.setColor(Color.blue);
            embed.setTimestamp(event.getTimeCreated());
            a.editOriginalEmbeds(embed.build()).queue();
        }else {
            Player.setLoop(false);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("unLoop Music");
            embed.addField("unLoop by","<@"+event.getMember().getId()+">",true);
            embed.addField("Command at","<#"+event.getGuildChannel().getId()+">",true);
            embed.addField("Voice channel","<#"+event.getMember().getVoiceState().getChannel().getId()+">",true);
            embed.setColor(Color.blue);
            embed.setTimestamp(event.getTimeCreated());
            a.editOriginalEmbeds(embed.build()).queue();
        }
        }
        }
    }
}
