package cherish.modules.impl.setting;

import cn.lzq.injection.leaked.invoked.UpdateEvent;
import cherish.event.annotations.EventTarget;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.setting.impl.ModeSetting;
import cherish.modules.setting.impl.NumberSetting;
import cherish.ui.notification.NotificationManager;
import cherish.ui.notification.NotificationType;

import java.awt.*;


public class Theme extends Module {
    public static Theme INSTANCE;
    public final ModeSetting color_Value = new ModeSetting("Theme Mode", this, new String[]{
            "Fade",
            "Static",
            "Rainbow",
            "Double"
    }, "Double");

    public final NumberSetting red = new NumberSetting("Red", this, 71, 0, 255, 1);
    public final NumberSetting green = new NumberSetting("Green", this, 148, 0, 255, 1);
    public final NumberSetting blue = new NumberSetting("Blue", this, 253, 0, 255, 1);

    public final NumberSetting red2 = new NumberSetting("Red 2", this, 71, 0, 255, 1);
    public final NumberSetting green2 = new NumberSetting("Green 2", this, 253, 0, 255, 1);
    public final NumberSetting blue2 = new NumberSetting("Blue 2", this, 160, 0, 255, 1);

    public Color firstColor = new Color(red.getValue().intValue(), green.getValue().intValue(), blue.getValue().intValue());
    public Color secondColor = new Color(red2.getValue().intValue(), green2.getValue().intValue(), blue2.getValue().intValue());

    public Theme() {
        super("Theme", "主题色", Category.SETTING);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        NotificationManager.add(NotificationType.INFO, "Theme", "你不用打开这个模块");
        this.setEnabled(false);
    }
}

