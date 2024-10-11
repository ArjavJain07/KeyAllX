package studio.xstream.keyallx.Display;

import net.minecraft.server.v1_8_R1.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TextDisplay_Abs_1_8_R1 implements TextDisplayAbs {

    @Override
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle time = new PacketPlayOutTitle(EnumTitleAction.TIMES, ChatSerializer.a(""), fadeIn, stay ,fadeOut);
        connection.sendPacket(time);

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"}"), 1, 1, 1);
        connection.sendPacket(titlePacket);

        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"), 1, 1, 1);
        connection.sendPacket(subtitlePacket);
    }

    @Override
    public void sendHotbar(Player player, String text) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{\"text\": \"" + text + "\"}"), (byte)2));
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
