package studio.xstream.keyallx.Commands;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.xstream.keyallx.Config.ConfigSettings;
import studio.xstream.keyallx.KeyAllX;
import studio.xstream.keyallx.KeyAllXManager;

import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    private static final List<String> COMPLETION_LIST = Lists.newArrayList("reload");

    private final KeyAllX plugin;
    private final KeyAllXManager manager;

    public ReloadCommand(KeyAllX plugin, KeyAllXManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String cmd, @NotNull String[] args) {
        if(sender instanceof Player && !sender.hasPermission("keyallx.reload")){
            sender.sendMessage("You don't have permissions to run this command");
            return true;
        }

        if(args.length < 1 || !args[0].equalsIgnoreCase("reload"))
            return true;

        ConfigSettings.reloadConfig(plugin);
        manager.reloadConfig(plugin.getConfig());
        sender.sendMessage("Config has been reloaded");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1)
            return COMPLETION_LIST;

        return null;
    }
}