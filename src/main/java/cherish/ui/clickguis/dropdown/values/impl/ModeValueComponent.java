package cherish.ui.clickguis.dropdown.values.impl;

import net.minecraft.client.gui.GuiGraphics;
import cherish.cherish;
import cherish.utils.font.TrueTypeFont;
import cherish.modules.impl.hud.HUD;
import cherish.modules.setting.Setting;
import cherish.modules.setting.impl.ModeSetting;
import cherish.ui.clickguis.dropdown.ModuleRenderer;
import cherish.ui.clickguis.dropdown.values.Component;
import cherish.utils.wrapper.Wrapper;

public class ModeValueComponent extends Component implements Wrapper {
    private final ModeSetting modeValue;

    public ModeValueComponent(Setting<?> value, ModuleRenderer parent, int offset) {
        super(value, parent, offset);
        if (!(value instanceof ModeSetting)) {
            throw new IllegalArgumentException("ModeSetting required");
        }
        this.modeValue = (ModeSetting) value;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, int x, int y, int width, int height) {
        super.render(guiGraphics, mouseX, mouseY, delta, x, y, width, height);

        String text = modeValue.getValue();
        TrueTypeFont font = getCurrentFont();

        // Draw the mode value
        font.drawString(guiGraphics.pose(), text,
                x + 7 + font.getStringWidth(modeValue.getName() + ": "),
                y + (height / 2F - font.getHeight() / 2F), -1);

        // Draw the colon
        font.drawString(guiGraphics.pose(), ": ",
                x + 7 + font.getStringWidth(modeValue.getName()),
                y + (height / 2F - font.getHeight() / 2F), -1);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY, parent.parent.x, parent.parent.y + parent.offset + offset,
                parent.parent.width, parent.parent.height) && mouseButton == 0) {

            String[] modes = modeValue.getValues();
            int nextIndex = (getCurrentIndex() + 1) % modes.length;
            modeValue.setValue(modes[nextIndex]);
            // Refresh state
            parent.parent.refreshModules();
        }
    }

    private int getCurrentIndex() {
        return java.util.Arrays.asList(modeValue.getValues()).indexOf(modeValue.getValue());
    }

    private TrueTypeFont getCurrentFont() {
        boolean isEnglish = ((HUD) cherish.INSTANCE.getModuleManager().getModule(HUD.class))
                .languageValue.getValue().equals("English");
        return isEnglish ?
                cherish.INSTANCE.getFontManager().tenacity20 :
                cherish.INSTANCE.getFontManager().zw20;
    }
}