package cherish.modules.impl.misc;

import cn.lzq.injection.leaked.invoked.WorldEvent;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import cherish.cherish;
import cherish.event.annotations.EventTarget;
import cherish.event.impl.Event;
import cherish.event.impl.PacketEvent;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.impl.movement.NoSlow;
import cherish.modules.setting.impl.BooleanSetting;
import cherish.utils.ClientUtils;
import cherish.utils.FuckerUtils;
import cherish.utils.TimerUtils;

import java.util.concurrent.LinkedBlockingQueue;

public class Disabler extends Module {
    public static Disabler INSTANCE;
    public final BooleanSetting post = new BooleanSetting("Post", this, false);
    public final BooleanSetting inventorySwingBypassed = new BooleanSetting("Inventory Swing Bypassed", this, true);
    public LinkedBlockingQueue<Packet<ClientGamePacketListener>> packets = new LinkedBlockingQueue<>();
    private boolean lastResult = false;
    public int inGameTick;
    private final TimerUtils swingTimer = new TimerUtils();

    public Disabler() {
        super("Disabler", "禁用器", Category.MISC);
        setEnabled(true);
        INSTANCE = this;
    }

    public boolean usePost() {
        boolean result = isEnabled()
                && post.getValue()
                && mc.player != null
                && mc.level != null
                && mc.player.isAlive()
                && inGameTick >= 60
                && !(mc.screen instanceof ProgressScreen);
        if (lastResult && !result) {
            lastResult = false;

            release();
            reset();
        }
        lastResult = result;
        return result;
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof ClientboundLoginPacket) {
            release();
            reset();
        }

        if (mc.screen instanceof InventoryScreen && (!NoSlow.INSTANCE.serverEat && inventorySwingBypassed.getValue())) {
            if (event.getPacket() instanceof ServerboundSwingPacket) {
                if (!swingTimer.hasTimeElapsed(250L, true)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @Override
    protected void onEnable() {
        setSuffix("Grim");

        release();
        reset();
    }

    @Override
    protected void onDisable() {
        release();
        reset();
    }

    @EventTarget
    public void onWorldChange(WorldEvent event) {
        release();
        reset();
    }

    public void reset() {
        inGameTick = 0;
        packets.clear();
    }

    public void release() {
        ClientGamePacketListener connection = mc.getConnection();

        if (connection == null) return;

        if (!packets.isEmpty()) {
            for (Packet<ClientGamePacketListener> packet : packets) {
                if (FuckerUtils.onPacket(packet, Event.Side.PRE)) continue;

                try {
                    packet.handle(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            packets.clear();
        }
    }

    public boolean delayPacket(Packet<?> packet) {
        return packet instanceof ClientboundExplodePacket
                || packet instanceof ClientboundSetEntityMotionPacket
                || packet instanceof ClientboundSetEntityDataPacket
                || packet instanceof ClientboundInitializeBorderPacket
                || packet instanceof ClientboundContainerSetSlotPacket
                || packet instanceof ClientboundUpdateAttributesPacket
                || packet instanceof ClientboundUpdateEnabledFeaturesPacket
                || packet instanceof ClientboundAddPlayerPacket
                || packet instanceof ClientboundPingPacket
                || packet instanceof ClientboundPongResponsePacket
                || packet instanceof ClientboundPlayerPositionPacket
                || packet instanceof ClientboundTeleportEntityPacket
                || packet instanceof ClientboundEntityEventPacket
                || packet instanceof ClientboundSetEquipmentPacket
                || packet instanceof ClientboundPlayerInfoUpdatePacket
                || packet instanceof ClientboundPlayerInfoRemovePacket
                || packet instanceof ClientboundRemoveEntitiesPacket
                || packet instanceof ClientboundKeepAlivePacket
                || packet instanceof ClientboundSetHealthPacket
                || packet instanceof ClientboundMoveEntityPacket
                || packet instanceof ClientboundOpenScreenPacket
                || packet instanceof ClientboundContainerSetContentPacket
                || packet instanceof ClientboundCustomPayloadPacket
                || packet instanceof ClientboundLevelEventPacket
                || packet instanceof ClientboundRemoveMobEffectPacket
                || packet instanceof ClientboundAddEntityPacket
                || packet instanceof ClientboundTakeItemEntityPacket
                || packet instanceof ClientboundAnimatePacket
                || packet instanceof ClientboundPlayerAbilitiesPacket;
    }
}
