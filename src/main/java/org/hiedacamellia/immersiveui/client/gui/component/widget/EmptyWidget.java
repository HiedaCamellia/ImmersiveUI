package org.hiedacamellia.immersiveui.client.gui.component.widget;

import net.minecraft.client.gui.GuiGraphics;

public class EmptyWidget extends ClickableWidget{
    public EmptyWidget() {
        super(0, 0);
    }

    @Override
    public void click(boolean click) {

    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, float x, float y) {

    }

    @Override
    public void hover(boolean hover) {

    }
}
