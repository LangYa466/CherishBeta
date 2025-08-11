package cherish.modules.impl.misc;

import cn.lzq.injection.leaked.invoked.LivingUpdateEvent;
import cn.lzq.injection.leaked.invoked.WorldEvent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import cherish.cherish;
import cherish.event.annotations.EventTarget;
import cherish.event.impl.PacketEvent;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.impl.combat.KillAura;
import cherish.modules.impl.player.ChestStealer;
import cherish.modules.impl.player.InvCleaner;
import cherish.modules.impl.player.Scaffold;
import cherish.modules.setting.impl.BooleanSetting;

public class ModuleHelper extends Module {
    public ModuleHelper() {
        super("ModuleHelper", "模块助手", Category.MISC);
    }

    private final BooleanSetting lagBackCheckValue = new BooleanSetting("LagBack Check", this, false);

    @EventTarget
    public void onPacketReceive(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof ClientboundLoginPacket || (packet instanceof ClientboundPlayerPositionPacket && lagBackCheckValue.getValue())) {
            disableModule();
        }
    }

    @EventTarget
    public void onWorld(WorldEvent event) {
        disableModule();
    }

    public void disableModule() {
        if (cherish.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled())
            cherish.INSTANCE.getModuleManager().getModule(KillAura.class).setEnabled(false);
        if (cherish.INSTANCE.getModuleManager().getModule(InvCleaner.class).isEnabled())
            cherish.INSTANCE.getModuleManager().getModule(InvCleaner.class).setEnabled(false);
        if (cherish.INSTANCE.getModuleManager().getModule(ChestStealer.class).isEnabled())
            cherish.INSTANCE.getModuleManager().getModule(ChestStealer.class).setEnabled(false);
        if (cherish.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled())
            cherish.INSTANCE.getModuleManager().getModule(Scaffold.class).setEnabled(false);
    }

    @EventTarget
    public void onUpdate(LivingUpdateEvent event) {
        if (mc.player == null) return;

        if (mc.player.isSpectator() || !mc.player.isAlive() || mc.player.isDeadOrDying()) {
            if (cherish.INSTANCE.getModuleManager().getModule(InvCleaner.class).isEnabled())
                cherish.INSTANCE.getModuleManager().getModule(InvCleaner.class).setEnabled(false);
            if (cherish.INSTANCE.getModuleManager().getModule(ChestStealer.class).isEnabled())
                cherish.INSTANCE.getModuleManager().getModule(ChestStealer.class).setEnabled(false);
        }
    }
}
