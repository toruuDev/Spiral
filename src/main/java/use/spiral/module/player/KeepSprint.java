package use.spiral.module.player;

import use.spiral.event.EventHook;
import use.spiral.event.impl.ClientTickEvent;
import use.spiral.module.Module;

import static use.spiral.Spiral.mc;

public class KeepSprint extends Module {
    public KeepSprint() {
        super("KeepSprint", "Keeps your sprint", Category.Player);
    }

    @EventHook
    public void onTick(ClientTickEvent event) {
        mc.player.setSprinting(true);
    }
}
