package cherish.modules.impl.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import cherish.cherish;
import cherish.event.annotations.EventTarget;
import cherish.event.impl.Render3DEvent;
import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.impl.combat.KillAura;
import cherish.modules.impl.hud.HUD;
import cherish.modules.setting.impl.BooleanSetting;
import cherish.modules.setting.impl.ModeSetting;
import cherish.modules.setting.impl.NumberSetting;
import cherish.utils.misc.EntityUtils;
import cherish.utils.render.ESPUtils;
import cherish.utils.render.RenderUtils;

import java.awt.*;

public class TargetMarker extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Nursultan", "Jello"}, "Nursultan");
    private final BooleanSetting onlyKillAura = new BooleanSetting("Only KillAura", this, false);
    private final NumberSetting range = new NumberSetting("Range", this, 4, 0.1, 6, 0.1);
    private float circleStep = 0;
    public TargetMarker() {
        super("TargetMarker", "目标指示器", Category.RENDER);
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if (mc.player == null || mc.level == null || event.poseStack() == null) return;
        PoseStack poseStack = event.poseStack();
        Color mainColor = HUD.INSTANCE.getColor(1);
        Color secondColor = HUD.INSTANCE.getColor(8);

        if (onlyKillAura.getValue()) {
            final Entity entity = KillAura.target;
            if (entity != null) {
                poseStack.pushPose();
                switch (mode.getValue()) {
                    case "Nursultan" -> ESPUtils.drawTextureOnEntity(poseStack, -24, -24, 48, 48, 48, 48, entity, cherish.INSTANCE.getCLIENT_TARGET_PNG(), true, mainColor, mainColor, secondColor, secondColor);
                    case "Jello" -> {
                        ESPUtils.drawJello(poseStack, entity, 1, circleStep);
                        circleStep += 0.08f;
                    }
                }
                poseStack.popPose();
            }
        } else {
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (EntityUtils.isSelected(entity, true) && mc.player.distanceTo(entity) <= range.getValue().floatValue()) {
                    poseStack.pushPose();
                    switch (mode.getValue()) {
                        case "Nursultan" -> ESPUtils.drawTextureOnEntity(poseStack, -24, -24, 48, 48, 48, 48, entity, cherish.INSTANCE.getCLIENT_TARGET_PNG(), true, mainColor, mainColor, secondColor, secondColor);
                        case "Jello" -> ESPUtils.drawJello(poseStack, entity, 1, circleStep);
                    }
                    poseStack.popPose();
                }
            }
            if (mode.is("Jello")) circleStep += 0.08f;
        }
    }
}
