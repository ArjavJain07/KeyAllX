package studio.xstream.keyallx.Config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

import java.time.Duration;

public class ConfigPaperHelper {

    public static Object createTitle(String titleMessage, String subTitleMessage, int titleFadeIn, int titleStay, int titleFadeOut){
        try{
            Component titleText = Component.text(titleMessage != null ? titleMessage : "");
            Component subtitle = Component.text(subTitleMessage != null ? subTitleMessage : "");

           Title.Times times = Title.Times.times(Duration.ofMillis(titleFadeIn * 50L),  Duration.ofMillis(titleStay * 50L), Duration.ofMillis(titleFadeOut * 50L));

            return net.kyori.adventure.title.Title.title(titleText, subtitle, times);
        }
        catch(Throwable e){
            return null;
        }
    }

    public static Object createHotbar(String hotbarMessage){
        try{
            return Component.text(hotbarMessage);
        }
        catch(Throwable e){
            return null;
        }
    }

}
