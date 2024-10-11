package studio.xstream.keyallx.Display;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class TextDisplay_Abs_1_11_Up implements TextDisplayAbs {

    @Override
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void sendHotbar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR ,new TextComponent(text));
    }

    @Override
    public void sendTitle(Player player, Object title) {
        if(title == null)
            throw new NullPointerException();

        Audience.audience(player).showTitle((Title) title);
    }

    @Override
    public void sendHotbarPaper(Player player, Object message) {
        if(message == null)
            throw new NullPointerException();

        Audience.audience(player).sendActionBar((ComponentLike) message);
    }
}
