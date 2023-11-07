package core.shibadev.main.lavalink;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class KmEvent extends ListenerAdapter {

    @Override
    public void onChannelDelete(ChannelDeleteEvent event) {
        if (event.getChannelType().equals(ChannelType.VOICE)) {

        }
    }
}
