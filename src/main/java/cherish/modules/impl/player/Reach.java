package cherish.modules.impl.player;

import cn.lzq.injection.leaked.invoked.MouseOverEvent;
import cherish.event.annotations.EventTarget;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.setting.impl.NumberSetting;

public class Reach extends Module {
    public final NumberSetting range = new NumberSetting("Range",this,3,3,6,0.1);
    public Reach() {
        super("Reach","距离", Category.PLAYER);
    }

    @EventTarget
    public void onMouseOverEvent(MouseOverEvent event) {
        event.setRange(Math.pow(range.getValue().doubleValue(), 2));
    }
}