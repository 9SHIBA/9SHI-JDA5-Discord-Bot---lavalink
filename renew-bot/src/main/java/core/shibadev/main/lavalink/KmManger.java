package core.shibadev.main.lavalink;

import com.google.gson.JsonObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.net.URLEncoder;
import java.util.HashMap;

public class KmManger {

    public final String SEARCH_PLATFORM = "ytsearch";
    public final KmVoiceUpdate voiceUpdate;
    public JDA jda;
    public KmEvent KmEvent;
    public String TOKEN_SPOTIFY = "";
    public HashMap<String, KmNode> NodeList = new HashMap<>(); // NodeName Socket
    public HashMap<Guild, KmPlayer> PlayerList = new HashMap<>(); // Guild Player

    private final String userId;

    public KmManger(String userId, JDA jda) {
        this.userId = userId;
        this.jda = jda;
        voiceUpdate = new KmVoiceUpdate(this);
    }

    public void addNode(String Name, String URL, String Pass, KmNode.Callback callback) {
        KmNode node = new KmNode(this, Name, URL, Pass, this.userId, callback);
        node.connect();
        NodeList.put(Name, node);
    }

    public KmPlayer getPlayer(Guild g) {
        return PlayerList.getOrDefault(g, null);
    }

    public KmVoiceUpdate getVoiceInterceptor() {
        return voiceUpdate;
    }

    public void init(JDA jda) {
        this.jda = jda;
        HashMap<String, String> params = new HashMap<String, String>(); params.put("grant_type", "client_credentials");
    }

    public JsonObject search(String search) {
        KmNode node = this.getNode();
        String BASE_API = "http://" + node.URL + "/loadtracks?identifier=";
        return KmUtils.searchLavaLink(BASE_API + URLEncoder.encode(search), node.Pass);
    }

    public KmNode getNode() {
        KmNode fin = null;
        for (KmNode node : this.NodeList.values()) {fin = node;break;}
        return fin;
    }

}
