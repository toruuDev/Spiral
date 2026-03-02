package use.spiral.ui.spiralgui;

import net.minecraft.client.gui.DrawContext;
import use.spiral.Spiral;
import use.spiral.module.Module;

import java.util.List;

import static use.spiral.Spiral.mc;

public class DropdownPanel {
    private final Module.Category category;

    private int x, y;
    private int width = 100;
    private int height = 18;

    private boolean expanded = true;

    public DropdownPanel(Module.Category category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
    }

    public void render(DrawContext ctx, int mx, int my) {
        ctx.fillGradient(
                x, y,
                x + width,
                y + height,
                0xFF2A2A2A,
                0xFF151515
        );

        ctx.drawText(
                mc.textRenderer,
                category.name(),
                x + 6,
                y + 5,
                0xFFFFFFFF,
                true
        );

        if(!expanded) return;
        int offsetY = y + height;

        List<Module> modules = Spiral.moduleManager.getModulesByCategory(category);

        for (Module module : modules) {
            int textColor = module.isEnabled()
                    ? 0xFF00FF88
                    : 0xFFAAAAAA;

            ctx.fillGradient(
                    x,
                    offsetY,
                    x + width,
                    offsetY + 16,
                    0xFF202020,
                    0xFF111111
            );

            ctx.drawText(
                    mc.textRenderer,
                    module.getName(),
                    x + 6,
                    offsetY + 4,
                    textColor,
                    true
            );

            offsetY += 16;
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;

        if (hovered && button == 0) {
            expanded = !expanded;
        }

        if (!expanded) return;

        int offsetY = y + height;

        for (Module module :
                Spiral.moduleManager.getModulesByCategory(category)) {

            boolean moduleHovered = mouseX >= x && mouseX <= x + width && mouseY >= offsetY && mouseY <= offsetY + 16;

            if (moduleHovered && button == 0) {
                module.toggle();
            }

            offsetY += 16;
        }
    }
}
