package use.spiral.mixin;

import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import use.spiral.Spiral;
import use.spiral.event.impl.SendMessageEvent;
import use.spiral.event.impl.SendPacketEvent;

@SuppressWarnings("all")

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin extends ClientCommonNetworkHandler {
    @Shadow public abstract void sendChatMessage(String ctx);

    @Unique private boolean ignoreChatMessage;

    protected ClientPlayNetworkHandlerMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState state) {
        super(client, connection, state);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo info) {
        if(ignoreChatMessage) return;
        boolean handled = Spiral.commandManager.handleChatMsg(message);

        if(handled) {
            info.cancel();
            return;
        }

        SendMessageEvent event = new SendMessageEvent(message);
        Spiral.eventBus.post(event);

        if(event.isCancelled()) {
            info.cancel();
            return;
        }

        if(!event.message.equals(message)) {
            ignoreChatMessage = true;
            sendChatMessage(event.message);
            ignoreChatMessage = false;
            info.cancel();
        }
    }

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        SendPacketEvent event = new SendPacketEvent(packet);
        Spiral.eventBus.post(event);

        if(event.isCancelled()) {
            ci.cancel();
        }
    }
}