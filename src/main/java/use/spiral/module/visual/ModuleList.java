package use.spiral.module.visual;

import net.minecraft.client.gui.DrawContext;
import use.spiral.Spiral;
import use.spiral.event.EventHook;
import use.spiral.event.impl.Render2DEvent;
import use.spiral.module.Module;

import use.spiral.module.Module;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static use.spiral.Spiral.mc;

public class ModuleList extends Module {
    public ModuleList() {
        super("Module List", "Shows active modules", Category.Visual);
    }

    @EventHook
    public void onRender2D(Render2DEvent event) {
        DrawContext ctx = event.getCtx();

        if(mc.player == null || mc.options.hudHidden) {
            return;
        }

        List<Module> enabledModules = Spiral.moduleManager.getModules()
                .stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparingInt(
                        m -> -mc.textRenderer.getWidth(m.getName())
                ))
                .collect(Collectors.toList());

        int screenWidth = mc.getWindow().getScaledWidth();
        int y = 0;
        int padding = 0;

        for (Module module : enabledModules) {
            String text = module.getName();
            int textWidth = mc.textRenderer.getWidth(text);

            int x = screenWidth - textWidth;

            ctx.fill(
                    x - padding, y - 2,
                    x + textWidth + padding,
                    y + mc.textRenderer.fontHeight + 2,
                    0x90000000
            );

            ctx.drawTextWithShadow(
                    mc.textRenderer,
                    text, x, y, Color.WHITE.getRGB()
            );

            y += mc.textRenderer.fontHeight+3;
        }
    }
}
