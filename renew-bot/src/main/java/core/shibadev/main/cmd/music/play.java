package core.shibadev.main.cmd.music;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import core.shibadev.main.Main;
import core.shibadev.main.lavalink.KmPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class play {
    public play(SlashCommandInteractionEvent event, InteractionHook a){
        if (event.getMember() != null && event.getMember().getVoiceState() != null && event.getMember().getVoiceState().getChannel() != null) {
            KmPlayer Player = Main.Manager.getPlayer(event.getGuild());
            String se = event.getOption("search").getAsString();
            if (Player == null) {
                Player = new KmPlayer(Main.Manager, event.getGuild(), event.getMember().getVoiceState().getChannel().getId(), event.getTextChannel().getId());
            }
            if (!Player.CONNECTED)
                Player.connect();
            JsonObject res;
            if (IsURL(se)) res = Main.Manager.search(se);
            else res = Main.Manager.search("ytsearch:" + se);
            String LoadType = res.get("loadType").getAsString();
            EmbedBuilder embed = new EmbedBuilder();
            if (LoadType.equals("PLAYLIST_LOADED")) {
                embed.setTitle("TrackLoaded");
                embed.setTimestamp(event.getTimeCreated());
                embed.setColor(Color.blue);
                int limit = 0;
                for (JsonElement tracks : res.get("tracks").getAsJsonArray()) {
                    JsonObject track = tracks.getAsJsonObject();
                    JsonObject info = track.get("info").getAsJsonObject();
                    limit = limit + 1;
                    if(limit < 10) {
                        String url = "https://www.youtube.com/watch?v=" + info.get("identifier").getAsString()+"/";
                    //    embed.appendDescription("\n**"+"[Video Link]("+url+") "+"Name : " + info.get("title").getAsString() + " (time:" + formatTime(info.get("length").getAsInt())+"**");
                        embed.appendDescription("\n**"+info.get("title").getAsString() + " [Video Link]("+url+")**");
                    }
                    Player.list.add(track);
                }
                //embed.appendDescription("```**Added " + limit + " Music**");

                a.editOriginalEmbeds(embed.build()).queue();
            } else if (LoadType.equals("TRACK_LOADED") || LoadType.equals("SEARCH_RESULT")) {
                for (JsonElement tracks : res.get("tracks").getAsJsonArray()) {
                    JsonObject track = tracks.getAsJsonObject();
                    JsonObject info = track.get("info").getAsJsonObject();
                    embed.setTitle(info.get("title").getAsString());
                    embed.setColor(Color.blue);
                    embed.setTimestamp(event.getTimeCreated());
                    embed.addField("duration","**"+formatTime(info.get("length").getAsInt())+"**",true);
                    embed.addField("author","**"+info.get("author").getAsString()+"**",true);
                    embed.addField("sourceName","**"+info.get("sourceName").getAsString()+"**",true);
                    embed.setThumbnail("https://i.ytimg.com/vi/" + info.get("identifier").getAsString() + "/maxresdefault.jpg");
                    embed.setFooter("Engine [V0.12β]",event.getGuild().getSelfMember().getAvatarUrl());
                    Player.list.add(track);
                    a.editOriginalEmbeds(embed.build()).queue();
                    break;
                }
            }
            if (!Player.PLAYING && !Player.list.isEmpty()) {
                JsonObject track = Player.list.get(0);
                Player.play(track.get("track").getAsString(), 50, false, false);
                Player.list.remove(0);
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Error . . . Some thing went wrong try again");
            builder.setImage("https://c.tenor.com/cw9FteFI798AAAAM/miku-meme.gif");
            builder.setColor(Color.blue);
            builder.setTimestamp(event.getTimeCreated());
            a.editOriginalEmbeds(builder.build()).queue();

        }
    }

    public static boolean IsURL(String s) {
        String regex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
//        String regex = "\\b(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]";
        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }
    public static String formatTime(long timeInMillis) {
        Duration duration = Duration.ofMillis(timeInMillis);
        return String.format("%d:%02d:%02d:%02d", duration.toDays(), duration.toHours() % 24, duration.toMinutes() % 60, duration.toSeconds() % 60);
    }
}
