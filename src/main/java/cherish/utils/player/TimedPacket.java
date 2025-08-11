/*
 * MoonLight Hacked Client
 *
 * A free and open-source hacked client for Minecraft.
 * Developed using Minecraft's resources.
 *
 * Repository: https://github.com/randomguy3725/MoonLight
 *
 * Author(s): [Randumbguy & wxdbie & opZywl & MukjepScarlet & lucas & eonian]
 */
package cherish.utils.player;


import net.minecraft.network.protocol.Packet;
import cherish.utils.TimerUtils;

public class TimedPacket {

    private final Packet packet;
    private final TimerUtils time;
    private final long millis;

    public TimedPacket(Packet packet) {
        this.packet = packet;
        this.time = new TimerUtils();
        this.millis = System.currentTimeMillis();
    }

    public TimedPacket(final Packet packet, final long millis) {
        this.packet = packet;
        this.millis = millis;
        this.time = null;
    }

    public Packet getPacket() {
        return packet;
    }

    public TimerUtils getCold() {
        return getTime();
    }

    public TimerUtils getTime() {
        return time;
    }

    public long getMillis() {
        return millis;
    }

}
