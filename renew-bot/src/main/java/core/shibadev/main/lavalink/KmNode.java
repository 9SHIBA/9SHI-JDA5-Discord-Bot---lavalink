package core.shibadev.main.lavalink;

import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.Guild;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class KmNode {

    public final KmManger KmManger;
    public final String Name;
    public final String URL;
    public final String Pass;
    public final String ClientID;
    public WebSocketClient Socket = null;

    public interface Callback {
        void onConnect(String Name);
        void onClose(int i, String s, boolean b, String Name);
        void onError(Exception e, String Name);
        void onTrackEndEvent(String reason, Guild guild, String track);
        void onTrackStartEvent(Guild guild, String track);
    }

    public KmNode(KmManger KmManger, String Name, String URL, String Pass, String ClientID, Callback callback) {
        this.KmManger = KmManger;
        this.Name = Name;
        this.URL = URL;
        this.Pass = Pass;
        this.ClientID = ClientID;

        Map<String, String> httpHeaders = new HashMap<String, String>();
        httpHeaders.put("Authorization", this.Pass);
        httpHeaders.put("Client-Name", this.Name);
        httpHeaders.put("User-Id", this.ClientID);

        try {
            Socket = new WebSocketClient(new URI("ws://" + URL), httpHeaders) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {callback.onConnect(Name);}
                @Override
                public void onMessage(String s) {
                    JsonObject json = KmUtils.StringJson(s);
                    if (json.get("op").getAsString().equals("event")) {
                        if (json.get("type").getAsString().equals("TrackEndEvent")) {
                            callback.onTrackEndEvent(json.get("reason").getAsString(), KmManger.jda.getGuildById(json.get("guildId").getAsString()), json.get("track").getAsString());
                        } else if (json.get("type").getAsString().equals("TrackStartEvent")) {
                            callback.onTrackStartEvent(KmManger.jda.getGuildById(json.get("guildId").getAsString()), json.get("track").getAsString());
                        }
                    }
                }
                @Override
                public void onClose(int i, String s, boolean b) {callback.onClose(i, s, b, Name);}
                @Override
                public void onError(Exception e) {callback.onError(e, Name);}
            };
        } catch (java.net.URISyntaxException ignored) {}

    }

    public boolean connect() {
        if (Socket != null) {
            Socket.connect(); return true;
        } else {
            return false;
        }
    }

}
