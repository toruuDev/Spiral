package use.spiral.module;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.InputUtil;
import use.spiral.Spiral;

@Getter
public class Module {
    private final String name;
    private final String description;
    private final Category category;

    private boolean enabled = false;
    @Setter
    @Getter
    private boolean wasPressed = false;

    @Getter
    @Setter
    private InputUtil.Key keybind = InputUtil.UNKNOWN_KEY;

    public enum Category {
        Combat,
        Player,
        Visual,
        Client
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Module(String name, String description, Category category, InputUtil.Key key) {
        this(name, description, category);
        this.keybind = key;
    }


    public void onEnable() {
        Spiral.eventBus.subscribe(this);
    }

    public void onDisable() {
        Spiral.eventBus.unsubscribe(this);

    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
        toggleMessage();

    }

    public void toggleMessage() {
        Spiral.sendChatMessage(name + " " + (enabled ? "was enabled" : "was disabled"));
    }
}
