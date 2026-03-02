package use.spiral.module.player;

import use.spiral.event.EventHook;
import use.spiral.event.impl.ClientTickEvent;
import use.spiral.module.Module;

import static use.spiral.Spiral.mc;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints for you", Category.Player);
    }

    @EventHook
    public void onTick(ClientTickEvent event) {
        if(mc.player == null) {
            return;
        }

        boolean moving = mc.player.input.movementForward > 0;
        boolean canSprint =
                moving &&
                        !mc.player.isSneaking() &&
                        !mc.player.horizontalCollision &&
                        mc.player.getHungerManager().getFoodLevel() >  6;

        if(canSprint) {
            mc.player.setSprinting(true);
        }
    }
}
