package use.spiral.command.impl;

import net.minecraft.client.util.InputUtil;
import use.spiral.Spiral;
import use.spiral.command.Command;
import use.spiral.manager.KeybindManager;

import use.spiral.module.Module;
import use.spiral.utils.InputUtils;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind", "Manage your keybinds", "b");
    }

    @Override
    public void runCommand(String[] args) {
        if(args.length < 1) {
            Spiral.sendChatMessage("Usage: .bind <set/remove/list/save/load>");
            return;
        }
        String action = args[0];

        switch (action) {
            case "set": {
                if(args.length < 3) {
                    Spiral.sendChatMessage("Usage: bind set <module> <key>");
                    return;
                }
                Module module = Spiral.moduleManager.getModuleByName(args[1]);
                if(module == null) {
                    Spiral.sendChatMessage("Module " + args[1] + " does not exist!");
                    return;
                }

                InputUtil.Key key = InputUtils.getKey(args[2]);
                if(key == InputUtil.UNKNOWN_KEY) {
                    Spiral.sendChatMessage("Unknown key: " + args[2]);
                    return;
                }

                module.setKeybind(key);
                KeybindManager.save();
                Spiral.sendChatMessage("Bound " + module.getName() +" to " + args[2]);
                break;
            }

            case "remove": {
                if(args.length < 2) {
                    Spiral.sendChatMessage("Usage: bind set <module> <key>");
                    return;
                }
                Module module = Spiral.moduleManager.getModuleByName(args[1]);
                if(module == null) {
                    Spiral.sendChatMessage("Module " + args[1] + " does not exist!");
                    return;
                }

                module.setKeybind(InputUtil.UNKNOWN_KEY);
                KeybindManager.save();
                Spiral.sendChatMessage("Unbound " + module.getName());
                break;
            }

            case "save": {
                KeybindManager.save();
                break;
            }

            case "load": {
                KeybindManager.load();
                break;
            }

            case "list": {
                Spiral.sendChatMessage("Keybinds: ");
                for(Module module : Spiral.moduleManager.getModules()) {
                    if(module.getKeybind() != InputUtil.UNKNOWN_KEY) {
                        String keyName = module.getKeybind().getTranslationKey().replace("key.keyboard.", "");
                        Spiral.sendChatMessage(module.getName() + ": " + keyName);
                    }
                }
                break;
            }

            default:
                break;
        }
    }
}