package cherish.modules;

import lombok.Getter;
import lombok.Setter;
import cherish.cherish;
import cherish.modules.impl.hud.HUD;
import cherish.modules.impl.hud.NotificationHUD;
import cherish.modules.setting.Setting;
import cherish.ui.notification.NotificationManager;
import cherish.ui.notification.NotificationType;
import cherish.utils.animations.Animation;
import cherish.utils.animations.Direction;
import cherish.utils.animations.impl.DecelerateAnimation;
import cherish.utils.sound.SoundUtil;
import cherish.utils.wrapper.Wrapper;

import java.util.ArrayList;

@Getter
public abstract class Module implements Wrapper {
    private final String name;
    private final String cnName;
    private final Category category;
    private final ArrayList<Setting<?>> settings;
    @Getter
    private final Animation animation = new DecelerateAnimation(300, 1).setDirection(Direction.BACKWARDS);
    @Setter
    private int key;
    private boolean enabled;
    @Setter
    private String suffix;
    @Setter
    private boolean expanded;

    public Module(String name, String cnName, Category category, int key) {
        this.name = name;
        this.cnName = cnName;
        this.category = category;
        this.key = key;
        this.settings = new ArrayList<>();
    }

    public Module(String name, String cnName, Category category) {
        this.name = name;
        this.cnName = cnName;
        this.category = category;
        this.key = 0;
        this.settings = new ArrayList<>();
    }

    public Setting<?> findSetting(String name) {
        for (Setting<?> setting : getSettings()) {
            if (setting.getName().replace(" ", "").equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public String getModuleName() {
        if (cherish.INSTANCE == null || cherish.INSTANCE.getModuleManager().getModule(HUD.class) == null)
            return null;

        return ((HUD) (cherish.INSTANCE.getModuleManager().getModule(HUD.class))).languageValue.getValue().equals("English") ? getName() : getCnName();
    }

    private String getEnableText() {
        if (cherish.INSTANCE == null || cherish.INSTANCE.getModuleManager().getModule(HUD.class) == null)
            return null;

        return ((HUD) (cherish.INSTANCE.getModuleManager().getModule(HUD.class))).languageValue.getValue().equals("English") ? " was §2Enabled" : "开启了";
    }

    private String getDisableText() {
        if (cherish.INSTANCE == null || cherish.INSTANCE.getModuleManager().getModule(HUD.class) == null)
            return null;

        return ((HUD) (cherish.INSTANCE.getModuleManager().getModule(HUD.class))).languageValue.getValue().equals("English") ? " was §4Disabled" : "关闭了";
    }

    private String getTitleName() {
        if (cherish.INSTANCE == null || cherish.INSTANCE.getModuleManager().getModule(HUD.class) == null)
            return null;

        return ((HUD) (cherish.INSTANCE.getModuleManager().getModule(HUD.class))).languageValue.getValue().equals("English") ? "Module" : "模块";
    }

    public void setEnabledWhenConfigChange(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            if (mc.player != null && mc.level != null) onEnable();
            cherish.INSTANCE.getEventManager().register(this);
        } else {
            if (mc.player != null && mc.level != null) cherish.INSTANCE.getEventManager().unregister(this);
            onDisable();
        }
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;

        this.enabled = enabled;
        if (enabled) {
            if (mc.player != null
                    && mc.level != null
                    && (cherish.INSTANCE != null
                    && cherish.INSTANCE.getModuleManager() != null
                    && cherish.INSTANCE.getModuleManager().getModule(NotificationHUD.class) != null
                    && NotificationHUD.notiStyle != null
                    && !NotificationHUD.notiStyle.equals("LSD"))) {
                NotificationManager.add(NotificationType.SUCCESS, getTitleName(), getModuleName() + getEnableText());
            } else if (mc.player != null && mc.level != null) {
                NotificationManager.add(NotificationType.SUCCESS, getTitleName(), getModuleName());
            }

            if (mc.player != null && mc.level != null) onEnable();
            cherish.INSTANCE.getEventManager().register(this);
            if (mc.player != null) SoundUtil.simplePlaySound(cherish.INSTANCE.getResourcesManager().resources.getAbsolutePath() + "\\sound\\enable.wav",.8f);
        } else {
            if (mc.player != null
                    && mc.level != null
                    && (cherish.INSTANCE != null
                    && cherish.INSTANCE.getModuleManager() != null
                    && cherish.INSTANCE.getModuleManager().getModule(NotificationHUD.class) != null
                    && NotificationHUD.notiStyle != null && !NotificationHUD.notiStyle.equals("LSD"))) {
                NotificationManager.add(NotificationType.DISABLE, getTitleName(), getModuleName() + getDisableText());
            } else if (mc.player != null && mc.level != null) {
                NotificationManager.add(NotificationType.DISABLE, getTitleName(), getModuleName());
            }

            cherish.INSTANCE.getEventManager().unregister(this);
            if (mc.player != null && mc.level != null) onDisable();
            if (mc.player != null) SoundUtil.simplePlaySound(cherish.INSTANCE.getResourcesManager().resources.getAbsolutePath() + "\\sound\\disable.wav",.8f);
        }
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }
}
