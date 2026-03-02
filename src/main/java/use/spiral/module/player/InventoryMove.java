package use.spiral.module.player;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.gui.screen.ingame.StructureBlockScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import use.spiral.event.EventHook;
import use.spiral.event.impl.ClientPostEvent;
import use.spiral.module.Module;

import java.util.Arrays;

import static use.spiral.Spiral.mc;

public class InventoryMove extends Module {

    public InventoryMove() {
        super("InventoryMove", "Move while in guis", Category.Player);
    }

    @EventHook
    public void onTick(ClientPostEvent event) {
        if(skip()) return;

        KeyBinding[] moveKeys = {
                mc.options.forwardKey,
                mc.options.backKey,
                mc.options.leftKey,
                mc.options.rightKey,
                mc.options.jumpKey,
                mc.options.sprintKey
        };

        moveKeys = Arrays.copyOf(moveKeys, moveKeys.length + 1);
        moveKeys[moveKeys.length - 1] = mc.options.sneakKey;

        for(KeyBinding key : moveKeys) {
            key.setPressed(isPressed(key));
        }
    }

    private boolean isPressed(KeyBinding key) {
        int code = InputUtil.fromTranslationKey(key.getBoundKeyTranslationKey()).getCode();
        return InputUtil.isKeyPressed(mc.getWindow().getHandle(), code);
    }

    public boolean skip() {
        if(mc.currentScreen instanceof CreativeInventoryScreen
                || mc.currentScreen instanceof ChatScreen
                || mc.currentScreen instanceof SignEditScreen
                || mc.currentScreen instanceof AnvilScreen
                || mc.currentScreen instanceof AbstractCommandBlockScreen
                || mc.currentScreen instanceof StructureBlockScreen
        ) return true;
        return false;
    }
}