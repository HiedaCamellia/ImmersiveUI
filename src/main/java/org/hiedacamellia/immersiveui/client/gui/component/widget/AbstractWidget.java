package org.hiedacamellia.immersiveui.client.gui.component.widget;

import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector2i;

public abstract class AbstractWidget {

    protected final int height,width;
    protected float scale;
    protected float renderScale;
    protected int x,y;
    protected int centerX,centerY;
    protected float alpha = 1.0f;

    public AbstractWidget(int length, int width) {
        this.height = length;
        this.width = width;
        this.renderScale = 1.0f;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(Vector2i pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public Vector2i getPos() {
        return new Vector2i(x, y);
    }

    public void setCenter(int x, int y) {
        this.centerX = x;
        this.centerY = y;
    }

    public float getX() {
        return x*scale+centerX;
    }

    public float getY() {
        return y*scale+centerY;
    }

    public int getHeight() {
        return (int) (height*scale);
    }

    public int getWidth() {
        return (int) (width*scale);
    }

    public abstract void render(GuiGraphics guiGraphics, float x, float y);

    public abstract void hover(boolean hover);

    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX >= getX()- (float) getWidth() /2 && mouseX <= getX() + (float) getWidth() /2
                && mouseY >= getY() - (float) getHeight() /2  && mouseY <= getY() + (float) getHeight() /2;
    }
}
