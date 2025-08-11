package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.ILayoutExtension;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.LayoutLocation;
import org.joml.Vector2f;

public interface IEmphasizeWidget extends ILayoutExtension {

    enum Emphasize {
        NONE,
        LINE_UNDER,
        LINE_OVER,
        BOLD
    }

    LayoutLocation getEmphasizeLocation();

    void setEmphasizeLocation(LayoutLocation location);

    Emphasize getEmphasize();

    void setEmphasize(Emphasize emphasize);

    int getEmphasizeColor();

    void setEmphasizeColor(int color);

    default Vector2f getOuterLineStart(){
        return getLocationPoint(getEmphasizeLocation());
    }

    default void renderEmphasize(GuiGraphics guiGraphics) {
        EmphasizeRenderHelper.renderEmphasize(this, guiGraphics, getEmphasizeColor(), getEmphasize());
    }

}
