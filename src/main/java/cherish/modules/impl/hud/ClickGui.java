package cherish.modules.impl.hud;

import cherish.cherish;
import cherish.modules.Category;
import cherish.modules.Module;
//import cherish.ui.clickguis.dropdown.ClickGUI;
import cherish.modules.setting.impl.ModeSetting;
import cherish.ui.clickguis.compact.CompactClickGUI;
import cherish.ui.clickguis.dropdown.DropdownClickGUI;
import org.lwjgl.glfw.GLFW;

public class ClickGui extends Module {
    public static ClickGui INSTANCE;
    public final ModeSetting mode = new ModeSetting("Mode", this, new String[]{
            "Compact",
            "Dropdown"
    }, "Compact");

    public ClickGui() {
        super("ClickGui", "点击界面" ,Category.RENDER, GLFW.GLFW_KEY_RIGHT_SHIFT);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        if (cherish.INSTANCE == null || cherish.INSTANCE.getConfigManager() == null || mc == null || mc.level == null || mc.player == null) return;

        if (mode.is("Compact")) {
            mc.setScreen(CompactClickGUI.INSTANCE);
        } else {
            mc.setScreen(DropdownClickGUI.INSTANCE);
        }

        cherish.INSTANCE.getConfigManager().save();
        setEnabled(false);
    }
}
