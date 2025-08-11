package cherish.command.commands;

import cherish.cherish;
import cherish.command.Command;
import cherish.modules.Module;
import cherish.modules.setting.Setting;
import cherish.modules.setting.impl.BooleanSetting;
import cherish.modules.setting.impl.ModeSetting;
import cherish.utils.ClientUtils;

public class SettingCommand extends Command {
    public SettingCommand() {
        super("value","setting");
    }

    @Override
    public void execute(String[] params) {
        if (params.length == 2) {
            Module module = cherish.INSTANCE.getModuleManager().findModule(params[0]);
            if (module != null) {
                Setting<?> setting = module.findSetting(params[0]);

                if (setting != null) {
                    // Setting-----------------------------------------------------
                    if (setting instanceof BooleanSetting wrapper) {
                        wrapper.setValue(Boolean.parseBoolean(params[1]));
                    }
                    if (setting instanceof ModeSetting wrapper) {
                        wrapper.setValue(String.valueOf(params[1]));
                    }
                    // Setting-----------------------------------------------------
                    ClientUtils.log("Set value " + setting.getName() + " to " + setting.getValue().toString());
                }
            } else {
                ClientUtils.log("Error: " + params[1] + " in " + module.getName() + " not found.");
            }
        } else {
            ClientUtils.log("Usage: .setting/value <module> <value>");
        }
    }
}
