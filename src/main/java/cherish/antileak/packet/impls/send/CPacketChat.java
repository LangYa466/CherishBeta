package cherish.antileak.packet.impls.send;

import cherish.antileak.packet.Packet;
import cherish.antileak.utils.PacketWrapper;
import io.netty.channel.Channel;

public class CPacketChat extends Packet {
    private final String message;

    public CPacketChat(Channel channel, String message) {
        super(channel);
        this.message = message;
    }

    @Override
    public void read(PacketWrapper wrapper) throws Throwable {
    }

    @Override
    public void write(PacketWrapper wrapper) throws Throwable {
        wrapper.writeString(message);
    }

    @Override
    public int packetId() {
        return 3;
    }
}
