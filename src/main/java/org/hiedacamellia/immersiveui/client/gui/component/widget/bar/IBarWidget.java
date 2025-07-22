package org.hiedacamellia.immersiveui.client.gui.component.widget.bar;

import net.minecraft.client.gui.GuiGraphics;

public interface IBarWidget {
    void vertical();
    void reverse();
    void setProgress(float progress);

    default void renderBeforeBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
    }

    default void renderAfterBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
    }
    void renderBorder(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
    void renderBar(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
    void renderBack(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

}
