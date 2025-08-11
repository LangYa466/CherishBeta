package cherish.modules.impl.render;

import cn.lzq.injection.leaked.invoked.TickEvent;
import cherish.event.annotations.EventTarget;
import cherish.modules.Category;
import cherish.modules.Module;

public class FullBright extends Module {
    private double originalGamma;

    public FullBright() {
        super("FullBright", "夜视", Category.RENDER);
        setEnabled(true);
    }

    /*@Override
    public void onEnable() {
        if (mc.player == null || mc.level == null) return;

        originalGamma = mc.options.gamma().get();
    }

    @Override
    public void onDisable() {
        if (mc.player == null || mc.level == null) return;

        mc.options.gamma().set(originalGamma);
    }

    @EventTarget
    public void onTick(TickEvent e) {
        if (mc.player == null || mc.level == null) return;

        if (mc.options.gamma().get() != 15.0D) {
            mc.options.gamma().set(15.0);
        }
    }*/
}