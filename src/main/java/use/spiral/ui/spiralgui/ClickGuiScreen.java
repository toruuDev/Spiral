package use.spiral.ui.spiralgui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import use.spiral.module.Module;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {
    private final List<DropdownPanel> panels = new ArrayList<>();

    public ClickGuiScreen() {
        super(Text.empty());

        int x = 20;

        for (Module.Category category : Module.Category.values()) {
            panels.add(new DropdownPanel(category, x, 40));
            x += 140;
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        for (DropdownPanel panel : panels) {
            panel.render(context, mouseX, mouseY);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        for (DropdownPanel panel : panels) {
            panel.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
