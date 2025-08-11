package cherish.modules.setting.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cherish.modules.Module;
import cherish.modules.setting.Setting;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, Module present, Boolean value) {
        super(name, present, value);
    }

    @Override
    public void toJson(JsonObject object) {
        object.addProperty(this.getName(), this.getValue());
    }

    @Override
    public void formJson(JsonElement element) {
        this.setValue(element.getAsBoolean());
    }
    public boolean isEnabled() {
        return value;
    }

    public void toggle() {
        setState(!isEnabled());
    }

    public void setState(boolean value) {
        this.value = value;
    }
}