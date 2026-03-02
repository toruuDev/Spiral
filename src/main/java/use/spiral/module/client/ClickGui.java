package use.spiral.module.client;

import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import use.spiral.module.Module;
import use.spiral.ui.spiralgui.ClickGuiScreen;

import static use.spiral.Spiral.mc;

public class ClickGui extends Module {
    public ClickGui() {
        super("ClickGui",
                "All the modules in the client",
                Category.Client,
                InputUtil.fromKeyCode(GLFW.GLFW_KEY_LEFT_SHIFT, 0)
        );
    }

    @Override
    public void onEnable() {
        mc.setScreen(new ClickGuiScreen());
    }

    @Override
    public void onDisable() {
        mc.setScreen(null);
    }
}
