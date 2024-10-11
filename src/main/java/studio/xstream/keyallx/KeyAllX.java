package studio.xstream.keyallx;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import studio.xstream.keyallx.Commands.ReloadCommand;
import studio.xstream.keyallx.Commands.StartAndStop;
import studio.xstream.keyallx.Config.ConfigSettings;
import studio.xstream.keyallx.PAPI.KeyAllXPlaceholder;

import java.util.Objects;

public final class KeyAllX extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ConfigSettings.reloadConfig(this);

        KeyAllXManager manager = new KeyAllXManager(this);

        getLogger().info("Loading Commands");

        ReloadCommand reloadCommand = new ReloadCommand(this, manager);
        StartAndStop startAndStop = new StartAndStop(manager);

        Objects.requireNonNull(getCommand("keyallx")).setExecutor(reloadCommand);
        Objects.requireNonNull(getCommand("keyallx")).setTabCompleter(reloadCommand);
        Objects.requireNonNull(getCommand("reset-timer")).setExecutor(startAndStop);
        Objects.requireNonNull(getCommand("stop-timer")).setExecutor(startAndStop);
        getLogger().info("Loaded Commands");

        manager.startTimer();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            new KeyAllXPlaceholder(this, manager).register();
        else
            getLogger().warning("PlaceholderAPI not found! Placeholder will not work.");

    }

}
