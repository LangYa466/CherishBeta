package cherish.modules.impl.hud;

import cherish.event.annotations.EventTarget;
import cherish.event.impl.Render2DEvent;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.ui.tabgui.CheckKeyUtils;
import cherish.ui.tabgui.TabGUI;

/**
 * @author DSJ_
 * @since 13/2/2025
 */
public class TabGui extends Module {
    public TabGui() {
        super("TabGUI", "选项卡", Category.RENDER);
    }
    @EventTarget
    public void onRender2D(Render2DEvent event) {
        CheckKeyUtils.checkKeyPress(); // 在每帧渲染时检查按键
        TabGUI.drawExhibitionTabGui(event.poseStack());
    }
}
