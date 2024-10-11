package studio.xstream.keyallx.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.xstream.keyallx.KeyAllX;
import studio.xstream.keyallx.KeyAllXManager;

public class StartAndStop implements CommandExecutor {

    private final KeyAllXManager manager;

    public StartAndStop(KeyAllXManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String cmd, @NotNull String[] args) {
        if(sender instanceof Player && !sender.hasPermission("keyallx.manipulate_timer")){
            sender.sendMessage("You don't have permissions to run this command");
            return true;
        }

        if(cmd.equalsIgnoreCase("reset-timer")){
            manager.startTimer();
            sender.sendMessage(ChatColor.GREEN + "Timer has been reset!");
        }
        else{
            manager.stopTimer();
            sender.sendMessage(ChatColor.GREEN + "Timer has been stopped!");
        }

        return true;
    }
}
