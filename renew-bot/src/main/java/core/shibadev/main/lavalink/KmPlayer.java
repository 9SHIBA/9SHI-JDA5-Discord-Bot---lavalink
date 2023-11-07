package core.shibadev.main.lavalink;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;

public class  KmPlayer {

    public final KmManger manger;
    public final Guild Guild;
    public final String voiceChannel;
    public final String textChannel;
    public KmNode node = null;
    public boolean PLAYING = false;
    public boolean CONNECTED = false;
    public JsonObject lastVoiceServerUpdate = null;
    public String lastSessionId = null;
    public boolean LOOP = false;
    public boolean loop = false;
    public JsonObject loopTrack = null;

    public ArrayList<JsonObject> looplist = new ArrayList<>();

    public ArrayList<JsonObject> list = new ArrayList<>();



    //LOOP
  //  public  boolean LOOP = false ;
  //  public ArrayList<JsonObject> looplist = new ArrayList<>();

    public KmPlayer(KmManger manger, Guild Guild, String voiceChannel, String textChannel) {
        this.manger = manger;
        this.Guild = Guild;
        this.voiceChannel = voiceChannel;
        this.textChannel = textChannel;
        for (KmNode node : manger.NodeList.values()) {
            this.node = node;
            break;
        }
        manger.PlayerList.put(Guild, this);
    }

    public boolean connect() {
        CONNECTED = true;
      //  LOOP = false;
        VoiceChannel vc = manger.jda.getVoiceChannelById(voiceChannel);
        if (vc != null && !Guild.getAudioManager().isConnected()) {
            AudioManager audio = Guild.getAudioManager();
            audio.openAudioConnection(vc);
            audio.setAutoReconnect(false);
            audio.setSelfDeafened(true);
            return true;
        } else {
            return false;
        }
    }

    public void destroy() {
        CONNECTED = false;
     //   LOOP = false;
        manger.PlayerList.remove(Guild);
        JsonObject json = new JsonObject();
        json.addProperty("op", "destroy");
        json.addProperty("guildId", Guild.getId());
        node.Socket.send(json.toString());
        AudioManager audio = Guild.getAudioManager();
        audio.closeAudioConnection();
    }
    public void play(String track, int Volume, boolean noReplace, boolean pause) {
        PLAYING = true;
        KmNode node = manger.getNode();
        JsonObject json = new JsonObject();
        json.addProperty("op", "play");
        json.addProperty("guildId", Guild.getId());

        if (LOOP && !looplist.isEmpty()) {
            JsonObject loopTrack = looplist.get(0);
            json.addProperty("track", loopTrack.get("track").getAsString());
            looplist.remove(0);
        } else {
            json.addProperty("track", track);
        }

        json.addProperty("volume", Volume + "");
        json.addProperty("noReplace", noReplace);
        json.addProperty("pause", pause);
        node.Socket.send(json.toString());
    }


    public void setLoopTrack(JsonObject track) {
        this.loopTrack = track;
    }
    public void setLoop(boolean loop) {
        LOOP = loop;
    }

    public boolean getLoop() {
        return LOOP;
    }
//    public void play(String track, int Volume, boolean noReplace, boolean pause) {
//        PLAYING = true;
//        KmNode node = manger.getNode();
//        JsonObject json = new JsonObject();
//        json.addProperty("op", "play");
//        json.addProperty("guildId", Guild.getId());
//        json.addProperty("track", track);
//        json.addProperty("volume", Volume + "");
//        json.addProperty("noReplace", noReplace);
//        json.addProperty("pause", pause);
//        node.Socket.send(json.toString());
//    }

    public void stop() {
        PLAYING = false;
        JsonObject json = new JsonObject();
        json.addProperty("op", "stop");
        json.addProperty("guildId", Guild.getId());
        node.Socket.send(json.toString());
    }

    public void pause(boolean pause) {
        JsonObject json = new JsonObject();
        json.addProperty("op", "pause");
        json.addProperty("guildId", Guild.getId());
        json.addProperty("pause", pause);
        node.Socket.send(json.toString());
    }

    public void volume(int volume) {
        JsonObject json = new JsonObject();
        json.addProperty("op", "volume");
        json.addProperty("guildId", Guild.getId());
        json.addProperty("volume", volume);
        node.Socket.send(json.toString());
    }

    public void skip() {
        if (!this.list.isEmpty()) {
            JsonObject track = list.get(0);
            play(track.get("track").getAsString(), 50, false, false);
            list.remove(0);
        }
    }
    public void onVoiceServerUpdate(JsonObject json, String sessionId) {
        this.lastSessionId = sessionId;
        this.lastVoiceServerUpdate = json;
        JsonObject out = new JsonObject();
        out.addProperty("op", "voiceUpdate");
        out.addProperty("sessionId", sessionId);
        out.addProperty("guildId", Guild.getId());
        out.add("event", json.get("d").getAsJsonObject());
        this.node.Socket.send(out.toString());
    }
    public void filter_bass(){
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());


        JsonArray bands = new JsonArray();

        JsonObject obj = new JsonObject();
        JsonObject obj1 = new JsonObject();
        JsonObject obj2 = new JsonObject();
        JsonObject obj3 = new JsonObject();
        JsonObject obj4 = new JsonObject();
        JsonObject obj5 = new JsonObject();
        JsonObject obj6 = new JsonObject();
        JsonObject obj7 = new JsonObject();
        JsonObject obj8 = new JsonObject();
        JsonObject obj9 = new JsonObject();
        JsonObject obj10 = new JsonObject();
        JsonObject obj11 = new JsonObject();
        JsonObject obj12 = new JsonObject();
        JsonObject obj13 = new JsonObject();

        obj.addProperty("band", 0);
        obj.addProperty("gain", 0.6);
        obj1.addProperty("band", 1);
        obj1.addProperty("gain", 0.67);
        obj2.addProperty("band", 2);
        obj2.addProperty("gain", 0.67);
        obj3.addProperty("band", 3);
        obj3.addProperty("gain", 0);
        obj4.addProperty("band", 4);
        obj4.addProperty("gain", -0.5);
        obj5.addProperty("band", 5);
        obj5.addProperty("gain", 0.15);
        obj6.addProperty("band", 6);
        obj6.addProperty("gain", -0.45);
        obj7.addProperty("band", 7);
        obj7.addProperty("gain", 0.23);
        obj8.addProperty("band", 8);
        obj8.addProperty("gain", 0.35);
        obj9.addProperty("band", 9);
        obj9.addProperty("gain", 0.45);
        obj10.addProperty("band", 10);
        obj10.addProperty("gain", 0.55);
        obj11.addProperty("band", 11);
        obj11.addProperty("gain", 0.6);
        obj12.addProperty("band", 12);
        obj12.addProperty("gain", 0.55);
        obj13.addProperty("band", 13);
        obj13.addProperty("gain", 0);
        bands.add(obj);
        bands.add(obj1);
        bands.add(obj2);
        bands.add(obj3);
        bands.add(obj4);
        bands.add(obj5);
        bands.add(obj6);
        bands.add(obj7);
        bands.add(obj8);
        bands.add(obj9);
        bands.add(obj10);
        bands.add(obj11);
        bands.add(obj12);
        bands.add(obj13);


        json.add("equalizer", bands);

        node.Socket.send(json.toString());
    }
    public void filter_soft(){
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());


        JsonArray bands = new JsonArray();

        JsonObject obj = new JsonObject();
        JsonObject obj1 = new JsonObject();
        JsonObject obj2 = new JsonObject();
        JsonObject obj3 = new JsonObject();
        JsonObject obj4 = new JsonObject();
        JsonObject obj5 = new JsonObject();
        JsonObject obj6 = new JsonObject();
        JsonObject obj7 = new JsonObject();
        JsonObject obj8 = new JsonObject();
        JsonObject obj9 = new JsonObject();
        JsonObject obj10 = new JsonObject();
        JsonObject obj11 = new JsonObject();
        JsonObject obj12 = new JsonObject();
        JsonObject obj13 = new JsonObject();

        obj.addProperty("band", 0);
        obj.addProperty("gain", 0);
        obj1.addProperty("band", 1);
        obj1.addProperty("gain", 0);
        obj2.addProperty("band", 2);
        obj2.addProperty("gain", 0);
        obj3.addProperty("band", 3);
        obj3.addProperty("gain", 0);
        obj4.addProperty("band", 4);
        obj4.addProperty("gain", 0);
        obj5.addProperty("band", 5);
        obj5.addProperty("gain", 0);
        obj6.addProperty("band", 6);
        obj6.addProperty("gain", 0);
        obj7.addProperty("band", 7);
        obj7.addProperty("gain", 0);
        obj8.addProperty("band", 8);
        obj8.addProperty("gain", -0.25);
        obj9.addProperty("band", 9);
        obj9.addProperty("gain", -0.25);
        obj10.addProperty("band", 10);
        obj10.addProperty("gain", -0.25);
        obj11.addProperty("band", 11);
        obj11.addProperty("gain", -0.25);
        obj12.addProperty("band", 12);
        obj12.addProperty("gain", -0.25);
        obj13.addProperty("band", 13);
        obj13.addProperty("gain", -0.25);
        bands.add(obj);
        bands.add(obj1);
        bands.add(obj2);
        bands.add(obj3);
        bands.add(obj4);
        bands.add(obj5);
        bands.add(obj6);
        bands.add(obj7);
        bands.add(obj8);
        bands.add(obj9);
        bands.add(obj10);
        bands.add(obj11);
        bands.add(obj12);
        bands.add(obj13);

        json.add("equalizer", bands);


        node.Socket.send(json.toString());

    }
    public void filter_clear(){
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());

        JsonArray bands = new JsonArray();
        JsonObject obj = new JsonObject();
        JsonObject obj1 = new JsonObject();

        obj.addProperty("band", 0);
        obj.addProperty("gain", 0);
        obj1.addProperty("band",1);
        obj1.addProperty("gain",0);
        bands.add(obj);
        bands.add(obj1);
        json.add("equalizer", bands);

        node.Socket.send(json.toString());

    }
    public void filter_karaoke (){
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());

        JsonObject obj = new JsonObject();
        obj.addProperty("level",1.0 );
        obj.addProperty("monoLevel", 1.0);
        obj.addProperty("filterBand", 220.0);
        obj.addProperty("filterWidth", 100.0);
        json.add("karaoke", obj);

        node.Socket.send(json.toString());
    }
    public void filter_slowmotion(){
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());

        JsonObject obj = new JsonObject();
        obj.addProperty("speed",0.5 );
        obj.addProperty("pitch", 1.0);
        obj.addProperty("rate", 0.8);
        json.add("timescale", obj);

        node.Socket.send(json.toString());
    }
    public void filter_vaporwave(){
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());

        JsonObject obj = new JsonObject();
        obj.addProperty("speed",0.8500000238418579 );
        obj.addProperty("pitch", 0.800000011920929);
        obj.addProperty("rate", 1);
        json.add("timescale", obj);

        node.Socket.send(json.toString());
    }
    public void filter_nightcore(){
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());

        JsonObject obj = new JsonObject();
        obj.addProperty("speed",1.2999999523162842 );
        obj.addProperty("pitch", 1.2999999523162842);
        obj.addProperty("rate", 1);
        json.add("timescale", obj);
        node.Socket.send(json.toString());
    }

    public void bassboost(float percentage) {
        JsonObject json = new JsonObject();
        json.addProperty("op", "filters");
        json.addProperty("guildId", Guild.getId());


        JsonArray bands = new JsonArray();

        final float multiplier = percentage / 100.00f;


         //   equalizer.setGain(i, BASS_BOOST[i] * multiplier);
            JsonObject obj1 = new JsonObject();
            JsonObject obj2 = new JsonObject();
            JsonObject obj3 = new JsonObject();
            JsonObject obj4 = new JsonObject();
            JsonObject obj5 = new JsonObject();
            JsonObject obj6 = new JsonObject();
            JsonObject obj7 = new JsonObject();
            JsonObject obj8 = new JsonObject();
            JsonObject obj9 = new JsonObject();
            JsonObject obj10 = new JsonObject();
            JsonObject obj11 = new JsonObject();
            JsonObject obj12 = new JsonObject();
            JsonObject obj13 = new JsonObject();
            JsonObject obj14 = new JsonObject();
            JsonObject obj15 = new JsonObject();

            obj1.addProperty("band", 1);
            obj1.addProperty("gain", BASS_BOOST[1] * percentage);
            obj2.addProperty("band", 2);
            obj2.addProperty("gain", BASS_BOOST[2] * percentage);
            obj3.addProperty("band", 3);
            obj3.addProperty("gain", BASS_BOOST[3] * percentage);
            obj4.addProperty("band", 4);
            obj4.addProperty("gain", BASS_BOOST[4] * percentage);
            obj5.addProperty("band", 5);
            obj5.addProperty("gain", BASS_BOOST[5] * percentage);
            obj6.addProperty("band", 6);
            obj6.addProperty("gain", BASS_BOOST[6] * percentage);
            obj7.addProperty("band", 7);
            obj7.addProperty("gain", BASS_BOOST[7] * percentage);
            obj8.addProperty("band", 8);
            obj8.addProperty("gain", BASS_BOOST[8] * percentage);
            obj9.addProperty("band", 9);
            obj9.addProperty("gain", BASS_BOOST[9] * percentage);
            obj10.addProperty("band", 10);
            obj10.addProperty("gain", BASS_BOOST[10] * percentage);
            obj11.addProperty("band", 11);
            obj11.addProperty("gain", BASS_BOOST[11] * percentage);
            obj12.addProperty("band", 12);
            obj12.addProperty("gain", BASS_BOOST[12] * percentage);
            obj13.addProperty("band", 13);
            obj13.addProperty("gain", BASS_BOOST[13] * percentage);
            obj14.addProperty("band", 14);
            obj14.addProperty("gain", BASS_BOOST[14] * percentage);
            obj15.addProperty("band", 15);
            obj15.addProperty("gain", BASS_BOOST[15] * percentage);

            bands.add(obj1);
            bands.add(obj2);
            bands.add(obj3);
            bands.add(obj4);
            bands.add(obj5);
            bands.add(obj6);
            bands.add(obj7);
            bands.add(obj8);
            bands.add(obj9);
            bands.add(obj10);
            bands.add(obj11);
            bands.add(obj12);
            bands.add(obj13);
            bands.add(obj14);
            bands.add(obj15);

        json.add("equalizer", bands);
        
        node.Socket.send(json.toString());


    }
    public static final float[] BASS_BOOST = {
            0.2f,
            0.15f,
            0.1f,
            0.05f,
            0.0f,
            -0.05f,
            -0.1f,
            -0.1f,
            -0.1f,
            -0.1f,
            -0.1f,
            -0.1f,
            -0.1f,
            -0.1f,
            -0.1f
    };
}
