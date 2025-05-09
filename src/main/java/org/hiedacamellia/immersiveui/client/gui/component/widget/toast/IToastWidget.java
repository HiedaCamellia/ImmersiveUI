package org.hiedacamellia.immersiveui.client.gui.component.widget.toast;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public interface IToastWidget {

    default void setMessage(String message){
        setMessage(Component.literal(message));
    }

    void setMessage(Component message);

    void setTimeout(float timeout);

    void renderToast(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
}
