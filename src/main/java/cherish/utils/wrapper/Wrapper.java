package cherish.utils.wrapper;

import net.minecraft.client.Minecraft;
import cherish.cherish;

public interface Wrapper {
    //请勿使用Minecraft.getInstance(), 妖猫给Minecraft.getInstance()写了stack检测
    Minecraft mc = cherish.INSTANCE.getMinecraft();
}
