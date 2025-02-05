package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector3f;

public interface W2SWidget {

    boolean isComputed();

    boolean isInScreen();

    boolean isSelectable();

    boolean shouldRender();

    boolean shouldBeRemoved();

    boolean shouldRemove();

    boolean shouldSmoothPosition();

    boolean shouldSkip();

    boolean click();

    boolean limitInScreen();

    void calculateRenderScale(float distance);

    void updateAlpha();

    void getWorldPos(Vector3f out);

    void setRemoved();

    void setInScreen(boolean inScreen);

    void setComputed();

    void setScreenPos(float x, float y);

    float getX();

    float getY();

    float getXO();

    float getYO();

    float getScale();

    void setX(float x);

    void setY(float y);

    void setXO(float xO);

    void setYO(float yO);

    void render(GuiGraphics guiGraphics, boolean highlight, float value, float deltaTicks);

}
