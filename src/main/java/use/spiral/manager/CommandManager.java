package use.spiral.manager;

import use.spiral.Spiral;
import use.spiral.command.Command;
import use.spiral.command.impl.BindCommand;
import use.spiral.command.impl.FriendCommand;
import use.spiral.command.impl.HelpCommand;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("all")
public class CommandManager {
    public static CommandManager INSTANCE;
    private final Map<String, Command> commands = new HashMap<>();
    private static final String prefix = ".";

    public CommandManager() {
        initialize();
    }

    public void initialize() {

        addCommands(
            new HelpCommand(),
            new BindCommand(),
            new FriendCommand()
        );
    }

    public void addCommands(Command... commandz) {
        for(Command command : commandz) {
            commands.put(command.getName().toLowerCase(), command);
        }
    }

    public Command getCommand(String name) {
        return commands.get(name.toLowerCase());
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    public int getCommandsCount() {
        return commands.size();
    }

    public boolean processCommand(String input) {
        if(input == null || input.isBlank()) return false;
        if(!input.startsWith(prefix)) return false;

        input = input.substring(prefix.length()).trim();
        String[] parts = input.split("\\s+");
        String commandName = parts[0];
        Command command = null;

        for(Command cmd : commands.values()) {
            if(cmd.matches(commandName)) {
                command = cmd;
                break;
            }
        }

        if(command == null) {
            Spiral.sendChatMessage("Unknown Command " + commandName + " use .help for a list of commands.");
            return true;
        }

        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        command.runCommand(args);
        return true;
    }

    public boolean handleChatMsg(String message) {
        return message.startsWith(prefix) && processCommand(message);
    }
}