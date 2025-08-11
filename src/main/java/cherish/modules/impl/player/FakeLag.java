package cherish.modules.impl.player;

import cn.lzq.injection.leaked.invoked.UpdateEvent;
import cherish.event.annotations.EventTarget;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.setting.impl.NumberSetting;
import cherish.utils.math.MathUtils;
import cherish.utils.player.PingSpoofUtils;

public class FakeLag extends Module {
    private final NumberSetting minMs = new NumberSetting("Min MS", this,200, 0, 5000, 1);
    private final NumberSetting maxMs = new NumberSetting("Max MS", this,200, 0, 2000, 1);

    public FakeLag() {
        super("FakeLag", "虚假延迟" , Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        int ms = (int) MathUtils.getAdvancedRandom(minMs.getValue().floatValue(), maxMs.getValue().floatValue());
        boolean blinkIncoming = true;
        PingSpoofUtils.spoof(ms, blinkIncoming, blinkIncoming, blinkIncoming, blinkIncoming, blinkIncoming, true);

        if (mc.player.hurtTime > 0) {
            PingSpoofUtils.dispatch();
        }
    }
}
