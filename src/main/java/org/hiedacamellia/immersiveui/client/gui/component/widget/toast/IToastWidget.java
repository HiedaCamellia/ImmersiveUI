package org.hiedacamellia.immersiveui.client.gui.component.widget.toast;

import net.minecraft.client.gui.GuiGraphics;

public interface IToastWidget {

    void setTimeout(float timeout);

    void renderToast(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
}
