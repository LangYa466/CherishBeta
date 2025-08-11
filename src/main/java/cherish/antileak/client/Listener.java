package cherish.antileak.client;

import cherish.antileak.Fucker;
import cherish.antileak.packet.impls.send.CPacketKeepAlive;
import cherish.antileak.utils.PacketWrapper;
import cherish.utils.wrapper.Wrapper;

public class Listener extends Thread implements Wrapper {
    @Override
    public void run() {
        while (true) {
            try {
                /*10 second heartbeat packet*/
                Thread.sleep(10000L);
                if(Client.channel != null && Fucker.userData != null)
                    PacketWrapper.release(new CPacketKeepAlive(Client.channel));
            } catch (Throwable ignored) {}
        }
    }
}