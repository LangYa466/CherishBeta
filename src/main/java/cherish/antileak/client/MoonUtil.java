package cherish.antileak.client;

import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import cherish.cherish;
import cherish.antileak.Fucker;
import cherish.utils.NativeUtils;
import cherish.utils.PacketUtils;
import cherish.utils.unsafe.UnsafeUtils;

import java.util.HashSet;

public class MoonUtil extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50000L);
                if (cherish.INSTANCE.getModuleManager() != null && Client.channel == null || !Client.channel.isActive() || !Fucker.login) {
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
                }
            } catch (Throwable ignored) {}
        }
    }
}