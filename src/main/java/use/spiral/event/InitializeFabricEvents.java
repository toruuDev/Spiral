package use.spiral.event;

import static use.spiral.Spiral.mc;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import use.spiral.Spiral;
import use.spiral.event.impl.*;

import use.spiral.module.Module;

@SuppressWarnings("all")
public class InitializeFabricEvents {
    public static void start() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if(client.player == null) return; 
            Spiral.eventBus.post(new ClientTickEvent());
        }); 

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
         //  if (client.player == null) return; // change this to get gui in mani menus 
            if(mc.getOverlay() instanceof SplashOverlay) return;
            if(mc.currentScreen instanceof ChatScreen) return; 
            if(mc.currentScreen instanceof InventoryScreen) return; 
            if(mc.currentScreen instanceof SignEditScreen) return; 
            
            Spiral.eventBus.post(new ClientPostEvent());

            for(Module module : Spiral.moduleManager.getModules()) {
                if(module.getKeybind() != InputUtil.UNKNOWN_KEY) {
                    boolean pressed = GLFW.glfwGetKey(mc.getWindow().getHandle(), module.getKeybind().getCode()) == GLFW.GLFW_PRESS;
                    if(pressed && !module.isWasPressed() && client.currentScreen == null) {
                        module.toggle();
                    }
                    module.setWasPressed(pressed);
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {

            if (mc.player == null) return;
            if (mc.getOverlay() instanceof SplashOverlay) return;

            Spiral.eventBus.post(new Render2DEvent(drawContext, tickDelta));
        });
    }
}
