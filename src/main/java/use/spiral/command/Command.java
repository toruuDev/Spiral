package use.spiral.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("all")
@AllArgsConstructor
@Getter
public abstract class Command {
    protected final String name;
    protected final String description;
    protected final String alias;

    public abstract void runCommand(String[] args);

    public boolean matches(String input) {
        if(input.equalsIgnoreCase(name)) return true;
        return alias != null && input.equalsIgnoreCase(alias);
    }
}