package org.hiedacamellia.immersiveui.client.gui.component.widget.bar;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.layouts.LayoutElement;

public interface IBarWidget extends Renderable, LayoutElement {
    void vertical();
    void reverse();
    void setProgress(float progress);

    float getProgress();

    default void renderBeforeBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }

    default void renderAfterBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }
    void renderBorder(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
    void renderBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
    void renderBack(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

}
