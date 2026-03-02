package use.spiral.module.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import use.spiral.event.EventHook;
import use.spiral.event.impl.ClientTickEvent;
import use.spiral.manager.FriendManager;
import use.spiral.module.Module;

import static use.spiral.Spiral.mc;

public class Aura extends Module {

    private final double range = 4.1;

    public PlayerEntity target;

    public Aura() {
        super("Aura", "Attacks entities around you", Category.Combat);
    }

    @EventHook
    public void onTick(ClientTickEvent event) {

        if (mc.player == null || mc.world == null) return;

        for (PlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;
            if (!player.isAlive()) continue;
            if (FriendManager.isFriend(player.getNameForScoreboard())) continue;

            if (mc.player.distanceTo(player) <= range) {

                this.target = player;

                if (mc.player.getAttackCooldownProgress(0.0f) >= 1.0f) {

                    mc.interactionManager.attackEntity(mc.player, player);
                    mc.player.swingHand(Hand.MAIN_HAND);

                    break;
                }
            }
        }
    }
}