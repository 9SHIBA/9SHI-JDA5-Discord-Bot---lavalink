package core.shibadev.main.lavalink;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.VoiceDispatchInterceptor;
import org.jetbrains.annotations.NotNull;

public class KmVoiceUpdate implements VoiceDispatchInterceptor {

    private final KmManger manager;

    public KmVoiceUpdate(KmManger manager) {
        this.manager = manager;
    }

    @Override
    public void onVoiceServerUpdate(@NotNull VoiceDispatchInterceptor.VoiceServerUpdate voiceServerUpdate) {
        JsonObject json = (JsonObject)(new JsonParser()).parse(new String(voiceServerUpdate.toData().toJson()));
        Guild guild = voiceServerUpdate.getGuild();
        if (guild.getSelfMember().getVoiceState() != null)
            manager.getPlayer(guild).onVoiceServerUpdate(json, guild.getSelfMember().getVoiceState().getSessionId());
    }

    @Override
    public boolean onVoiceStateUpdate(@NotNull VoiceDispatchInterceptor.VoiceStateUpdate voiceStateUpdate) {
        AudioChannel channel = voiceStateUpdate.getChannel();
        if (channel != null) {
            KmPlayer p = manager.getPlayer(voiceStateUpdate.getGuild());
            return p.CONNECTED;
        } else {
            return false;
        }
    }

}
