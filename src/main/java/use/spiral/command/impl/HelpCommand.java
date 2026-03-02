package use.spiral.command.impl;

import use.spiral.Spiral;
import use.spiral.command.Command;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "HELP MEEE!!!!!", "h");
    }

    @Override
    public void runCommand(String[] args) {
        Spiral.sendChatMessage("Commands: ");
        for (Command command : Spiral.commandManager.getCommands()) {
            Spiral.sendChatMessage(
                    "§7" + command.getName() + " §8- §7" + command.getDescription()
            );
        }
    }
}
