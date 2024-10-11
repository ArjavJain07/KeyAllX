package studio.xstream.keyallx.Config;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import studio.xstream.keyallx.Display.TextDisplayAbs;

import java.util.List;

public class ReminderObj {

    private final List<String> message;
    private final String hotbar;
    private final Object hotBarPaper;
    private final String titleMessage;
    private final String subTitleMessage;
    private final int titleFadeIn;
    private final int titleStay;
    private final int titleFadeOut;
    private final Object paperTitle;
    private final Sound sound;
    private final float volume;
    private final float pitch;

    private final boolean canUseHotbar;
    private final boolean canUseTitle;

    public ReminderObj(List<String> message, String backupMessage, String hotbar, String titleMessage, String subTitleMessage, int titleFadeIn, int titleStay, int titleFadeOut, Sound sound, float volume, float pitch) {
        this.message = message;
        this.hotbar = hotbar;
        this.titleMessage = titleMessage;
        this.subTitleMessage = subTitleMessage;
        this.titleFadeIn = Math.max(1, titleFadeIn);
        this.titleStay = Math.max(1, titleStay);
        this.titleFadeOut = Math.max(1, titleFadeOut);
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;

        if(message.isEmpty() && backupMessage != null)
            message.add(backupMessage);

        canUseHotbar = hotbar != null && !hotbar.trim().isEmpty();
        canUseTitle = !((titleMessage == null && subTitleMessage == null) || ((titleMessage != null && titleMessage.trim().isEmpty()) && (subTitleMessage != null && subTitleMessage.trim().isEmpty())));

        if(canUseHotbar)
            this.hotBarPaper = ConfigSettings.createHotbar(hotbar);
        else
            this.hotBarPaper = null;

        if(canUseTitle)
            this.paperTitle = ConfigSettings.createTitle(titleMessage, subTitleMessage, titleFadeIn, titleStay, titleFadeOut);
        else
            this.paperTitle = null;
    }

    public void sendMessage(Player player){
        message.forEach(player::sendMessage);
    }

    public void sendHotbar(Player player, TextDisplayAbs display){
        try{
            display.sendHotbarPaper(player, hotBarPaper);
        }
        catch (Throwable e){
            if(canUseHotbar)
                display.sendHotbar(player, hotbar);
        }
    }

    public void sendTitle(Player player, TextDisplayAbs display){
        try{
            display.sendTitle(player, paperTitle);
        }
        catch(Throwable e){
            if(canUseTitle)
                display.sendTitle(player, titleMessage, subTitleMessage, titleFadeIn, titleStay, titleFadeOut);
        }
    }

    public void playSound(Player player){
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

}
