package cherish.modules.impl.player;

import cn.lzq.injection.leaked.invoked.MotionEvent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import cherish.event.annotations.EventTarget;
import cherish.event.impl.PacketEvent;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.setting.impl.NumberSetting;
import cherish.ui.notification.NotificationManager;
import cherish.ui.notification.NotificationType;
import cherish.utils.TimerUtils;


public class AutoReplay extends Module {
    public static int rounds = 0;
    private final TimerUtils timer = new TimerUtils();
    private boolean shouldReplay = false;
    private long triggerTime = 0;

    public NumberSetting delay = new NumberSetting("Delay", this,1000, 0, 5000, 100);

    public AutoReplay() {
        super("AutoReplay", "自动游戏", Category.PLAYER);
    }

    @EventTarget
    public void onChatReceived(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof ClientboundSystemChatPacket wrapper) {

            if (!isEnabled() || wrapper.content().getString().isEmpty()) return;

            String message = wrapper.content().getString();
            String playerName = mc.player.getGameProfile().getName();

            if (message.startsWith("<" + playerName + ">")) return;

            if (message.contains("游戏结束")) {
                rounds++;
                shouldReplay = true;
                triggerTime = System.currentTimeMillis() + (long) delay.getValue();
                NotificationManager.add(NotificationType.INFO, "AutoReplay", "准备自动开始下一局！");
            }
        }
    }

    @EventTarget
    public void onUpdate(MotionEvent event) {
        if (!isEnabled() || !shouldReplay) return;

        if (System.currentTimeMillis() >= triggerTime) {
            shouldReplay = false;

            ItemStack stack = mc.player.getInventory().getItem(4);
            if (stack.getItem() != Items.EMERALD) {
                NotificationManager.add(NotificationType.INFO, "AutoReplay","操你妈！");
                return;
            }

            int originalSlot = mc.player.getInventory().selected;

            try {
                mc.player.getInventory().selected = 4;
                //PacketUtils.sendPacket(new ServerboundUseItemPacket(InteractionHand.MAIN_HAND));
                timer.reset();
            } finally {
                mc.player.getInventory().selected = originalSlot;
            }
        }
    }
}