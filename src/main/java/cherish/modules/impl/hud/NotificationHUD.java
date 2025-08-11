package cherish.modules.impl.hud;

import cherish.cherish;
import cherish.event.annotations.EventTarget;
import cherish.event.impl.Render2DEvent;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.setting.impl.ModeSetting;
import cherish.ui.notification.NotificationManager;
import cherish.ui.notification.NotificationType;

/**
 * @author Jon_awa / DSJ_
 * @since 7/2/2025
 */
public class NotificationHUD extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Corner", "Center", "Top"}, "Corner");
    private final ModeSetting style = (ModeSetting) new ModeSetting("Style", this, new String[]{"cherish", "Exhibition", "Xylitol","LSD"}, "cherish")
            .setVisibility(() -> mode.is("Corner") || mode.is("Center"));
    private final ModeSetting color = (ModeSetting) new ModeSetting("Exhibition Color", this, new String[]{"White", "Black", "Classic","Alpha"}, "White")
            .setVisibility(() -> style.is("Exhibition") || mode.is("Corner") || mode.is("Center"));
    private final ModeSetting animation = (ModeSetting)  new ModeSetting("Animation", this, new String[]{"Side", "Zoom"}, "Side")
            .setVisibility(() -> mode.is("Corner") || mode.is("Center"));
    public static String notiMode;
    public static String notiStyle;
    public static String notiColor;
    public static String notiAnimation;
    public NotificationHUD() {
        super("Notification", "通知显示", Category.RENDER);
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        notiMode = mode.getValue();
        notiStyle = style.getValue();
        notiColor = color.getValue();
        notiAnimation = animation.getValue();

        if (cherish.INSTANCE.getModuleManager().getModule(HUD.class).isEnabled()) {
            if (mode.is("Top")) {
                NotificationManager.drawTopNotifications(event.poseStack());
            } else {
                NotificationManager.drawNotifications(event.poseStack());
            }
        }
    }
}
