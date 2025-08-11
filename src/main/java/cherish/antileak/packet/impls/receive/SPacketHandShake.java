package cherish.antileak.packet.impls.receive;

import cherish.cherish;
import cherish.antileak.Fucker;
import cherish.antileak.packet.Packet;
import cherish.antileak.user.UserType;
import cherish.antileak.utils.PacketWrapper;
import io.netty.channel.Channel;
import cherish.utils.WindowsUtil;
import cherish.utils.unsafe.UnsafeUtils;

public class SPacketHandShake extends Packet {
    public SPacketHandShake(Channel channel) {
        super(channel);
    }

    @Override
    public void read(PacketWrapper wrapper) throws Throwable {
        final int state = wrapper.readInt();

        switch (state) {
            case -1: // 传入参数错误
                WindowsUtil.error("传入参数错误!");
                Fucker.login = false;
                cherish.INSTANCE.getModuleManager().modules.clear();
                cherish.INSTANCE.setCommandManager(null);
                cherish.INSTANCE.setMinecraft(null);
                cherish.INSTANCE.setModuleManager(null);
                cherish.INSTANCE.setEventManager(null);
                UnsafeUtils.freeMemory();
                System.exit(0);
            break;

            case 1: // 登录成功
                Fucker.userData = wrapper.readUser();

                final String name = Fucker.userData.username;
                final boolean isBeta = Fucker.userData.isBeta;
                final UserType userType = UserType.getFromName(Fucker.userData.userLimits.tag);

                Fucker.login = true;
                Fucker.userType = userType;
                Fucker.name = name;
                Fucker.isBeta = isBeta;

                if (!Fucker.firstLogin) return;
                Fucker.firstLogin = false; // 破处

                cherish.INSTANCE.init();
                if (isBeta) {
                    WindowsUtil.success("验证成功! 尊敬的Beta用户 -> " + name);
                } else {
                    WindowsUtil.success("验证成功! 用户 -> " + name);
                }
                break;

            case 2: // 账密错误
                WindowsUtil.error("账密错误!");
                Fucker.login = false;
                cherish.INSTANCE.getModuleManager().modules.clear();
                cherish.INSTANCE.setCommandManager(null);
                cherish.INSTANCE.setMinecraft(null);
                cherish.INSTANCE.setModuleManager(null);
                cherish.INSTANCE.setEventManager(null);
                UnsafeUtils.freeMemory();
                System.exit(0);
                break;

            case 3: // UUID错误
                WindowsUtil.error("UUID错误-验证失败!");
                Fucker.login = false;
                cherish.INSTANCE.getModuleManager().modules.clear();
                cherish.INSTANCE.setCommandManager(null);
                cherish.INSTANCE.setMinecraft(null);
                cherish.INSTANCE.setModuleManager(null);
                cherish.INSTANCE.setEventManager(null);
                UnsafeUtils.freeMemory();
                System.exit(0);
                break;

            case 4: // 时间不足
                WindowsUtil.error("时间不足!");
                Fucker.login = false;
                cherish.INSTANCE.getModuleManager().modules.clear();
                cherish.INSTANCE.setCommandManager(null);
                cherish.INSTANCE.setMinecraft(null);
                cherish.INSTANCE.setModuleManager(null);
                cherish.INSTANCE.setEventManager(null);
                UnsafeUtils.freeMemory();
                System.exit(0);
                break;

            case 6: // 未允许的版本号
                WindowsUtil.error("未允许的版本号!");
                Fucker.login = false;
                cherish.INSTANCE.getModuleManager().modules.clear();
                cherish.INSTANCE.setCommandManager(null);
                cherish.INSTANCE.setMinecraft(null);
                cherish.INSTANCE.setModuleManager(null);
                cherish.INSTANCE.setEventManager(null);
                UnsafeUtils.freeMemory();
                System.exit(0);
                break;
        }
    }

    @Override
    public void write(PacketWrapper wrapper) throws Throwable {
    }

    @Override
    public int packetId() {
        return 0;
    }
}
