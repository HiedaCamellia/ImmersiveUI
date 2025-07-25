package org.hiedacamellia.immersiveui.client.gui.component.widget.bar;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

public abstract class AbstractBarWidget extends AbstractWidget implements IBarWidget {

    /**
     * Progress from 0 to 1
     */
    protected float progress = 0f;

    public void setProgress(float progress) {
        this.progress = Math.max(0f, Math.min(progress, 1f));
    }

    public float getProgress() {
        return progress;
    }


    public AbstractBarWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        renderBack(guiGraphics, i, i1, v);
        renderBeforeBar(guiGraphics, i, i1, v);
        renderBar(guiGraphics, i, i1, v);
        renderAfterBar(guiGraphics, i, i1, v);
        renderBorder(guiGraphics, i, i1, v);
    }
}
