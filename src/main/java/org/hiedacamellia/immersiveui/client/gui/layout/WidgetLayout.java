package org.hiedacamellia.immersiveui.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;
import org.hiedacamellia.immersiveui.client.gui.component.widget.AbstractWidget;

public class WidgetLayout extends Layout {

    private final AbstractWidget widget;

    public WidgetLayout(AbstractWidget widget, int index) {
        super(index);
        this.widget = widget;
    }

    @Override
    public void render(GuiGraphics guiGraphics, float width, float height) {
        widget.render(guiGraphics, x, y, width, height);
    }

    @Override
    public int getWidth() {
        return widget.getWidth();
    }

    @Override
    public int getHeight() {
        return widget.getHeight();
    }

}
