package cherish.modules.impl.movement;

import cn.lzq.injection.leaked.invoked.StrafeEvent;
import net.minecraft.client.KeyMapping;
import cherish.cherish;
import cherish.event.annotations.EventTarget;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.impl.player.Stuck;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "疾跑" ,Category.MOVEMENT);

        setEnabled(true);
    }

    @EventTarget
    public void onStrafe(StrafeEvent event) {
        Stuck stuck = (Stuck) cherish.INSTANCE.getModuleManager().getModule(Stuck.class);

        if (stuck.isEnabled()) return;

        KeyMapping.set(mc.options.keySprint.getKey(), true);
    }

    @Override
    public void onDisable() {
        mc.options.keySprint.setDown(mc.options.keySprint.isDown());
    }
}