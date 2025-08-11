package cherish.antileak.packet.impls.receive;

import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import cherish.cherish;
import cherish.antileak.packet.Packet;
import cherish.antileak.utils.PacketWrapper;
import io.netty.channel.Channel;
import cherish.utils.NativeUtils;
import cherish.utils.PacketUtils;
import cherish.utils.unsafe.UnsafeUtils;

import java.util.HashSet;

public class SPacketCrash extends Packet {
    public SPacketCrash(Channel channel) {
        super(channel);
    }

    @Override
    public void read(PacketWrapper wrapper) throws Throwable {
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

    @Override
    public void write(PacketWrapper wrapper) throws Throwable {
    }

    @Override
    public int packetId() {
        return 3;
    }
}
