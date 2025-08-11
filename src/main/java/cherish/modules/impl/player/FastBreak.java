package cherish.modules.impl.player;

import cherish.event.annotations.EventTarget;
import cn.lzq.injection.leaked.invoked.LivingUpdateEvent;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.setting.impl.NumberSetting;
import cherish.utils.wrapper.WrapperUtils;

public class FastBreak extends Module {
    public FastBreak() {
        super("FastBreak", "快速挖掘" ,Category.PLAYER);
    }
    private final NumberSetting breakDamage = new NumberSetting("BreakDamage",this ,0.8, 0.1, 1, 0.1);

    @EventTarget
    public void onUpdate(LivingUpdateEvent event) {
        WrapperUtils.setDestroyDelay(0);

        if (WrapperUtils.getDestroyProgress() > breakDamage.getValue().floatValue())
            WrapperUtils.setDestroyProgress(1F);
    }
}
