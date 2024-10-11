package studio.xstream.keyallx;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import studio.xstream.keyallx.Config.ConfigSettings;
import studio.xstream.keyallx.Config.ReminderObj;
import studio.xstream.keyallx.Display.*;
import studio.xstream.keyallx.Timer.KeyTimer;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class KeyAllXManager {

    private final KeyAllX plugin;
    private final TextDisplayAbs textDisplay;
    private final KeyTimer keyTimer;
    private final HashMap<Integer, ReminderObj> messages;
    private Integer taskId;

    public KeyAllXManager(KeyAllX plugin) {
        this.plugin = plugin;
        this.textDisplay = setupVersion();
        this.messages = new HashMap<>();
        reloadConfig(plugin.getConfig());
        this.keyTimer = new KeyTimer(plugin.getLogger(), textDisplay, messages);

        if(textDisplay != null)
            return;

        plugin.getLogger().warning("Invalid version detected, plugin will shut down...");
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    public void startTimer(){
        stopTimer();
        taskId = Bukkit.getScheduler().runTaskTimer(plugin, keyTimer, 20, 20).getTaskId();
    }

    public void stopTimer() {
        if(taskId != null)
            Bukkit.getScheduler().cancelTask(taskId);

        keyTimer.assignInterval();
    }

    public int getTimerInterval(){
        return keyTimer.getInterval();
    }

    public void reloadConfig(FileConfiguration config){
        messages.clear();

        //Original message using the same format so users won't have to change anything
        messages.put(0, new ReminderObj(ConfigSettings.getMessage(), null, ConfigSettings.getHotbarMessage(), ConfigSettings.getTitleMessage(), ConfigSettings.getSubTitleMessage(), ConfigSettings.getTitleFadeIn(), ConfigSettings.getTitleStay(), ConfigSettings.getTitleFadeOut(), ConfigSettings.getSound(), ConfigSettings.getVolume(), ConfigSettings.getPitch()));

        ConfigurationSection sec = config.getConfigurationSection("reminders");

        if(sec == null)
            return;

        Set<String> keys = sec.getKeys(false);

        if(keys.isEmpty())
            return;

        for(String key : keys){
            Integer interval = ConfigSettings.getInteger(key);

            if(interval == null || interval < 1)
                continue;

            String path = "reminders." + key + ".";
            List<String> message = ConfigSettings.color(config.getStringList(path + "message"));
            String backupMessage = ConfigSettings.translateColorCodes(config.getString(path + "message"));
            String hotbar = ConfigSettings.translateColorCodes(config.getString(path + "hotbar"));
            String title = ConfigSettings.translateColorCodes(config.getString(path + "title"));
            String subtitle = ConfigSettings.translateColorCodes(config.getString(path + "subtitle"));
            int fadeIn = config.getInt(path + "fadeIn");
            int stay = config.getInt(path + "stay");
            int fadeOut = config.getInt(path + "fadeOut");
            Sound sound = ConfigSettings.getSound(config.getString(path + "sound"));
            float volume = (float) config.getDouble(path + "volume");
            float pitch = (float) config.getDouble(path + "pitch");

            messages.put(interval, new ReminderObj(message, backupMessage, hotbar, title, subtitle, fadeIn, stay, fadeOut, sound, volume, pitch));
        }
    }

    public TextDisplayAbs getTextDisplay(){
        return textDisplay;
    }

    private TextDisplayAbs setupVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        String v = version.equalsIgnoreCase("org.bukkit.craftbukkit") ? "v1_11_R1" : version.split("\\.")[3];

        if (is11OrUp(v))
            return new TextDisplay_Abs_1_11_Up();

        switch (v) {
            case "v1_8_R1":
                return new TextDisplay_Abs_1_8_R1();

            case "v1_8_R2":
                return new TextDisplay_Abs_1_8_R2();

            case "v1_8_R3":
                return new TextDisplay_Abs_1_8_R3();

            case "v1_9_R1":
                return new TextDisplay_Abs_1_9_R1();

            case "v1_9_R2":
                return new TextDisplay_Abs_1_9_R2();

            case "v1_10_R1":
                return new TextDisplay_Abs_1_10_R1();

            default:
                return null;
        }
    }

    private boolean is11OrUp(String s) {
        if(s.split("_").length < 3)
            return false;

        Integer p0 = ConfigSettings.getInteger(s.split("_")[1]);
        Integer p1 = ConfigSettings.getInteger(s.split("_")[0]);

        return (p0 != null && p0 >= 11) || (p1 != null && p1 >= 2);
    }

}
