package use.spiral.module.visual;

import net.minecraft.client.gui.DrawContext;
import use.spiral.Spiral;
import use.spiral.event.EventHook;
import use.spiral.event.impl.Render2DEvent;
import use.spiral.module.Module;

import java.awt.*;

import static use.spiral.Spiral.mc;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", "Shows the Spiral watermark", Category.Visual);
    }

    @EventHook
    public void onRender2D(Render2DEvent event) {
        DrawContext ctx = event.getCtx();

        ctx.drawTextWithShadow(mc.textRenderer, "Spiral", 5, 5, Color.BLUE.getRGB());
    }
}
