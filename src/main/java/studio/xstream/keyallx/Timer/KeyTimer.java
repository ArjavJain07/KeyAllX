package studio.xstream.keyallx.Timer;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import studio.xstream.keyallx.Config.ConfigSettings;
import studio.xstream.keyallx.Config.ReminderObj;
import studio.xstream.keyallx.Display.TextDisplayAbs;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class KeyTimer implements Runnable {

    private final Logger logger;
    private final TextDisplayAbs display;
    private final ConsoleCommandSender consoleSender;
    private final HashMap<Integer, ReminderObj> messages;
    private int interval;

    public KeyTimer(Logger logger, TextDisplayAbs display, HashMap<Integer, ReminderObj> messages) {
        this.logger = logger;
        this.display = display;
        this.consoleSender = Bukkit.getConsoleSender();
        this.messages = messages;
        assignInterval();
    }

    public void run(){
        sendInteraction(--this.interval);

        if(this.interval > 0)
            return;

        assignInterval();

        dispatchCommand();
    }

    private void sendInteraction(int interval){
        ReminderObj reminderObj = messages.get(interval);

        if(reminderObj == null)
            return;

        for(Player player : Bukkit.getOnlinePlayers()){
            reminderObj.sendMessage(player);
            reminderObj.sendHotbar(player, display);
            reminderObj.sendTitle(player, display);
            reminderObj.playSound(player);
        }
    }

    public int getInterval(){
        return interval;
    }

    private void dispatchCommand(){
        boolean debugEnabled = ConfigSettings.isDebug();
        List<String> commands = ConfigSettings.getCommand();
        List<String> perPlayerCommands = ConfigSettings.getPerPlayerCommand();
        try {
            if (debugEnabled) {
                logger.info("Debug mode is enabled.");
                logger.info("Commands to dispatch: ");
                commands.forEach(logger::info);
                logger.info("\n");

                logger.info("Commands to dispatch per player: ");
                perPlayerCommands.forEach(logger::info);
                logger.info("\n");
            }

            if (Bukkit.getServer().getOnlinePlayers().isEmpty()) {
                if (debugEnabled)
                    logger.info("No online players to dispatch command to.");

                return;
            }

            commands.forEach(command -> Bukkit.dispatchCommand(consoleSender, command));

            if (debugEnabled)
                logger.info("Commands dispatched successfully.");

            if(perPlayerCommands.isEmpty())
                return;

            for(Player player : Bukkit.getOnlinePlayers())
                ConfigSettings.getPerPlayerCommand(player.getName()).forEach(command -> Bukkit.dispatchCommand(consoleSender, command));

            if (debugEnabled)
                logger.info("Per player commands dispatched successfully.");
        }
        catch (Throwable e) {
            logger.warning("Error while dispatching command: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void assignInterval(){
        this.interval = ConfigSettings.getTimeInterval();
    }

}
