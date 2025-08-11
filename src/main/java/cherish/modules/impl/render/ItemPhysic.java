package cherish.modules.impl.render;

import cherish.modules.Category;
import cherish.modules.Module;
import cherish.modules.setting.impl.NumberSetting;

public class ItemPhysic extends Module {
    public NumberSetting rotateSpeed = new NumberSetting("Rotate Speed", this, 300, 0, 500, 50);

    public static ItemPhysic INSTANCE;

    public ItemPhysic() {
        super("ItemPhysic", "物理掉落", Category.RENDER);
        INSTANCE = this;
    }
}
