package cherish.antileak.packet.impls.send;

import cherish.antileak.Fucker;
import cherish.antileak.packet.Packet;
import cherish.antileak.utils.CryptUtil;
import cherish.antileak.utils.PacketWrapper;
import io.netty.channel.Channel;

import java.nio.charset.StandardCharsets;

public class CPacketKeepAlive extends Packet {
    public CPacketKeepAlive(Channel channel) {
        super(channel);
    }

    @Override
    public void read(PacketWrapper wrapper) throws Throwable {
    }

    @Override
    public void write(PacketWrapper wrapper) throws Throwable {
        wrapper.writeBytes(CryptUtil.Sign.sign((String.valueOf(Fucker.userData.index.hashCode() ^ 3 & 31) + Fucker.userData.uuid.hashCode()).getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public int packetId() {
        return -1;
    }
}
