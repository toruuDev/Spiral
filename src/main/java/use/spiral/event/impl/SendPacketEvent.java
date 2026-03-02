package use.spiral.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.packet.Packet;
import use.spiral.event.Event;

@Getter
@Setter
@AllArgsConstructor
public class SendPacketEvent extends Event {
    private final Packet<?> packet;
}
