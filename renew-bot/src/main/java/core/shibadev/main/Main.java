package core.shibadev.main;

import com.google.gson.JsonObject;
import core.shibadev.main.cmd.SlashcommandEvent;
import core.shibadev.main.lavalink.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import static core.shibadev.main.api.Color.*;
import javax.security.auth.login.LoginException;


public class Main {
    public static JDA PublicJDA = null;
    public static final KmManger Manager = new KmManger("894495806948778036", PublicJDA);

    public static void main(String[] args) throws LoginException {
        JDABuilder jda = JDABuilder.createDefault("ODk0NDk1ODA2OTQ4Nzc4MDM2.YVq2Iw.BDmoY6XnWwDXg07TNd9jgD2v6YA",GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES);
        registry(jda);
        jda.setVoiceDispatchInterceptor(Manager.getVoiceInterceptor());
        PublicJDA = jda.build();
        command(PublicJDA.updateCommands());



        Manager.addNode("KmLavaLink01", KmConfig.LAVALINK_URL, KmConfig.LAVALINK_PASS, new KmNode.Callback() {
            @Override
            public void onConnect(String Name) {
                System.out.print(String.format(ANSI_GREEN+"[%s] Connected!\n", Name));
            }
            @Override
            public void onClose(int i, String s, boolean b, String Name) {
                System.out.print(String.format(ANSI_YELLOW+"[%s] Disconnected!\n", Name));
            }
            @Override
            public void onError(Exception e, String Name) {
                System.out.print(String.format(ANSI_RED+"[%s] Error Can't Disconnected! %s\n", Name, e.toString()));
            }
            @Override
            public void onTrackEndEvent(String reason, Guild guild, String track) {
                if (reason.equals("FINISHED")) {
                    KmPlayer player = Main.Manager.getPlayer(guild);
                    if (player.list.isEmpty()) {
                        // Loop the playlist if in loop mode
                        if (player.loop) {
                            JsonObject trackk = player.loopTrack;
                            player.play(trackk.get("track").getAsString(), 50, false, false);
                        }
                    } else {
                        JsonObject trackk = player.list.get(0);
                        player.play(trackk.get("track").getAsString(), 50, false, false);
                        player.list.remove(0);
                    }
                }
            }
//            @Override
//            public void onTrackEndEvent(String reason, Guild guild, String track) {
//                if (reason.equals("FINISHED")) {
//                    KmPlayer player = Main.Manager.getPlayer(guild);
//                    if (player.list.isEmpty()) {
//                        player.stop();
//                        player.destroy();
//                    } else {
//                        JsonObject trackk = player.list.get(0);
//                        player.play(trackk.get("track").getAsString(), 50, false, false);
//                        player.list.remove(0);
//                    }
////                    player.stop();
////                    player.destroy();
//                }
//            }

            @Override
            public void onTrackStartEvent(Guild guild, String track) {

            }
        });
    }

    public static void registry(JDABuilder jda) {
        jda.addEventListeners(new SlashcommandEvent());
    }
    //Command
    public static void command(CommandListUpdateAction commands){
        commands.addCommands(
                Commands.slash("play", "Music | added Music!")
                        .addOptions(new OptionData(OptionType.STRING, "search", "music url | youtube name")
                                .setRequired(true))
        );


        commands.addCommands(
                Commands.slash("stop","Music | Stop the music!")
        );
        commands.addCommands(
                Commands.slash("skip","Music | Skip the Music")
        );
        commands.addCommands(
                Commands.slash("volume","Music | Set Music volume").addOptions(new OptionData(OptionType.INTEGER,"volume","set a bot volume").setRequired(true))
        );
        commands.addCommands(
                Commands.slash("bassboost","Music | Use Bass boot").addOptions(new OptionData(OptionType.INTEGER,"bassboost","Enter percentage").setRequired(true))
        );
        commands.addCommands(
                Commands.slash("pause","Music | Pause Music").addOptions(new OptionData(OptionType.BOOLEAN,"pause","true or false").setRequired(true))
        );
        commands.addCommands(
                Commands.slash("loop","Music | Loop the music")
        );
        commands.addCommands(
                Commands.slash("help","Normal | About Bot commands")
        );
        commands.addCommands(
                Commands.slash("status","Dev info | Get bot performance")
        );
        commands.addCommands(
                Commands.slash("mcstatus","Mc Status | get server Status").addOptions(new OptionData(OptionType.STRING,"ip","minecraft ip").setRequired(true)).addOption(OptionType.INTEGER,"port","minecraft port")
        );
        commands.addCommands(
               Commands.slash("filter","Music | Fixed Filter").addOptions(new OptionData(OptionType.STRING, "fixed_filter", "filter mode")
                       .addChoice("bass","Bass Mode")
                       .addChoice("karaoke","Karaoke Mode")
                       .addChoice("soft","Soft Mode")
                       .addChoice("vaporwave","Vaporwave Mode")
                       .addChoice("nightcore","NightCore Mode")
                       .addChoice("slowmotion","Slowmotion Mode")
                       .addChoice("clear","clear filter")
                .setRequired(true))
        );

        commands.submit();
    }


}
