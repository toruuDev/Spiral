package use.spiral.module.combat;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import use.spiral.event.EventHook;
import use.spiral.event.impl.SendPacketEvent;
import use.spiral.module.Module;

import static use.spiral.Spiral.mc;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", "Always critical hits", Category.Combat);
    }

    @EventHook
    public void onPacket(SendPacketEvent event) {
        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0625, z, false));
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
    }
}
