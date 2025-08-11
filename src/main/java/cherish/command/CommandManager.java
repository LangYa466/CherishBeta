package cherish.command;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import cherish.cherish;
import cherish.command.commands.*;
import cherish.event.annotations.EventTarget;
import cherish.event.impl.PacketEvent;
import cherish.utils.ClientUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String,Command> commands = new HashMap<>();

    public CommandManager() {
        cherish.INSTANCE.getEventManager().register(this);

        registerCommand(new SettingCommand());
        registerCommand(new QQCommand());
        registerCommand(new BindCommand());
        registerCommand(new ConfigCommand());
    }

    private void registerCommand(Command command) {
        for (String name : command.getName()) {
            commands.put(name.toLowerCase(),command);
        }
    }

    @EventTarget
    public void onChat(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof ServerboundChatPacket wrapper) {
            if (wrapper.message().startsWith(".")) {
                String[] ars = wrapper.message().substring(1).split(" ");
                String name = ars[0];
                Command command = commands.get(name.toLowerCase());

                if (command == null) {
                    ClientUtils.log("Error: " + name + " is not a command.");
                } else {
                    command.execute(Arrays.copyOfRange(ars, 1, ars.length));
                }

                event.setCancelled(true);
            }
        }
    }
}
