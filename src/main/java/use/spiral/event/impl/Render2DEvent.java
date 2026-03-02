package use.spiral.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.DrawContext;

@Getter
@AllArgsConstructor
public class Render2DEvent {
    private final DrawContext ctx;
    private final float tick;
}
