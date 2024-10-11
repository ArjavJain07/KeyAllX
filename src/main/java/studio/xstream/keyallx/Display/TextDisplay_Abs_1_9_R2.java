package studio.xstream.keyallx.Display;

import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TextDisplay_Abs_1_9_R2 implements TextDisplayAbs {

    @Override
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, IChatBaseComponent.ChatSerializer.a(""), fadeIn, stay ,fadeOut);
        connection.sendPacket(time);

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"), 1, 1, 1);
        connection.sendPacket(titlePacket);

        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"), 1, 1, 1);
        connection.sendPacket(subtitlePacket);
    }

    @Override
    public void sendHotbar(Player player, String text) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}"), (byte)2));
    }

    @Override
    public void sendTitle(Player player, Object title) {
        throw new UnsupportedOperationException("Only for newer versions of paper.");
    }

    @Override
    public void sendHotbarPaper(Player player, Object message) {
        throw new UnsupportedOperationException("Only for newer versions of paper.");
    }
}
