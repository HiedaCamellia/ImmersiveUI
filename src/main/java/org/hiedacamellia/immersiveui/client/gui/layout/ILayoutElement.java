package org.hiedacamellia.immersiveui.client.gui.layout;

import net.minecraft.client.gui.GuiGraphics;

import java.util.Map;

public interface ILayoutElement {

    void render(GuiGraphics guiGraphics,float width,float height);

    float getX();

    float getY();

    int getWidth();

    int getHeight();

    void build();

    void add(ILayoutElement layout);

    int size();

    ILayoutElement get(int index);

    int getIndex();

    Map<Integer,ILayoutElement>  getObjects();

    void size(int width, int height);

    void position(float x, float y);
}
