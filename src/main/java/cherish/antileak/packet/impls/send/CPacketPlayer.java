package cherish.antileak.packet.impls.send;

import cherish.antileak.packet.Packet;
import cherish.antileak.utils.PacketWrapper;
import io.netty.channel.Channel;
import cherish.utils.wrapper.Wrapper;

public class CPacketPlayer extends Packet implements Wrapper {
    public CPacketPlayer(Channel channel) {
        super(channel);
    }

    @Override
    public void read(PacketWrapper wrapper) throws Throwable {
    }

    @Override
    public void write(PacketWrapper wrapper) throws Throwable {
        if (mc.player != null) {
            wrapper.writeString(mc.player.getScoreboardName());
        }
    }

    @Override
    public int packetId() {
        return 2;
    }
}
