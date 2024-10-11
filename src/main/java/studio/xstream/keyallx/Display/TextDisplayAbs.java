package studio.xstream.keyallx.Display;

import org.bukkit.entity.Player;

public interface TextDisplayAbs {

    void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut);
    void sendHotbar(Player player, String text);
    void sendTitle(Player player, Object title);
    void sendHotbarPaper(Player player, Object message);

}
