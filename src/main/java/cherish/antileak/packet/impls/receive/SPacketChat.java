package cherish.antileak.packet.impls.receive;

import cherish.antileak.packet.Packet;
import cherish.antileak.user.UserData;
import cherish.antileak.user.UserType;
import cherish.antileak.utils.PacketWrapper;
import io.netty.channel.Channel;
import cherish.utils.ClientUtils;

public class SPacketChat extends Packet {
    public SPacketChat(Channel channel) {
        super(channel);
    }

    @Override
    public void read(PacketWrapper wrapper) throws Throwable {
        final UserData sender = wrapper.readUserOthers();
        final String message = wrapper.readString();

        ClientUtils.displayIRC(UserType.getFromName(sender.userLimits.tag).getDisplayName() + "§r " + sender.playerName + " (" + sender.username + ") : " + message);
    }

    @Override
    public void write(PacketWrapper wrapper) throws Throwable {
    }

    @Override
    public int packetId() {
        return 4;
    }
}
