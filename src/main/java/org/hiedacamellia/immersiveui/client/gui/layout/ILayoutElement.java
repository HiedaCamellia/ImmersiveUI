package org.hiedacamellia.immersiveui.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;

public interface ILayoutElement {

    void render(GuiGraphics guiGraphics, float x, float y,float width,float height);

    int getWidth();

    int getHeight();

    void build();

    void add(ILayoutElement layout);
}
