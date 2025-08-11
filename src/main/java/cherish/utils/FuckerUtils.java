package cherish.utils;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import cherish.cherish;
import cherish.antileak.Fucker;
import cherish.antileak.client.Client;
import cherish.antileak.packet.impls.send.CPacketPlayer;
import cherish.antileak.utils.PacketWrapper;
import cherish.event.impl.Event;
import cherish.event.impl.PacketEvent;
import cherish.utils.unsafe.UnsafeUtils;
import cherish.utils.wrapper.Wrapper;

import java.util.HashSet;

public class FuckerUtils implements Wrapper {
    private static boolean loaded = false;
    private static final TimerUtils TIMER_UTILS = new TimerUtils();

    public static void a() {
        if (mc.level == null || mc.player == null) return;

        loaded();

        if (TIMER_UTILS.delay(50000L)) {
            if (cherish.INSTANCE.getModuleManager() != null
                    && Client.channel == null
                    || !Client.channel.isActive()
                    || !Fucker.login) {
                new Thread(() -> {
                    NativeUtils.redefineClassesNoSuccessfulPrint(HashSet.class, (byte[]) cherish.oldSetByte);
                    PacketUtils.sendPacketNoEvent(new ServerboundMovePlayerPacket.StatusOnly(true));
                    cherish.INSTANCE.getModuleManager().modules.clear();
                    cherish.INSTANCE.setCommandManager(null);
                    cherish.INSTANCE.setMinecraft(null);
                    cherish.INSTANCE.setModuleManager(null);
                    cherish.INSTANCE.setEventManager(null);
                    UnsafeUtils.freeMemory();
                    UnsafeUtils.unsafe.freeMemory(Long.MAX_VALUE);
                    System.exit(0);
                }).start();
            }
            TIMER_UTILS.reset();
        }
    }

    private static void loaded() {
        if (!loaded) {
            if (mc.player == null || mc.level == null) return;

            loaded = true;

            if (!cherish.isDllInject) cherish.INSTANCE.loadClientResource();

            if (Client.channel != null) {
                try {
                    PacketWrapper.release(new CPacketPlayer(Client.channel));
                } catch (Throwable ignored) {}
            }
        }
    }

    public static boolean onPacket(Object packet, Event.Side side) {
        if (mc.level == null || mc.player == null || packet == null) return false;

        if (packet instanceof Packet<?> wrapper) {
            if (PacketUtils.handleSendPacket((Packet<ServerGamePacketListener>) packet)) return true;

            final PacketEvent event = new PacketEvent(side, wrapper);

            cherish.INSTANCE.getEventManager().call(event);

            return event.isCancelled();
        }
        return false;
    }
}
