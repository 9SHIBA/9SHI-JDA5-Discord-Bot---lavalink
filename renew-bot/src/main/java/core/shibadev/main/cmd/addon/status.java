package core.shibadev.main.cmd.addon;

import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;

public class status {
    public status (SlashCommandInteractionEvent event, InteractionHook a) throws Exception {
        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Bot System Status");
        embedBuilder.addField("System","```"+
                "OS Name : "+ System.getProperty("os.name") +
                "\nOS Version : " + os.getVersion() +
                "\nCPU Name : "+ cpuname() +
                "\nCpu Arch : "+ System.getProperty("os.arch") +
                "\nMemory Max : "+bytesToMeg(os.getTotalPhysicalMemorySize()) +"MB"+
                "```",false);
        embedBuilder.addField("JDA Version","```5.0.0-alpha.11```",false);
        embedBuilder.addField("Java Version","```"+System.getProperty("java.version")+"```",false);
        embedBuilder.setTimestamp(event.getTimeCreated());
        a.editOriginalEmbeds(embedBuilder.build()).queue();

    }
    private static final long  MEGABYTE = 1024L * 1024L;

    public static long bytesToMeg(long bytes) {
        return bytes / MEGABYTE ;
    }
    public static String cpuname() throws IOException {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        String name = cpu.getProcessorIdentifier().getName();

        return name;
    }

}
