package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.LayoutLocation;

public abstract class EmphasizeWidget extends AbstractWidget implements IEmphasizeWidget {

    protected LayoutLocation emphasizeLocation = LayoutLocation.NONE;
    protected Emphasize emphasize = Emphasize.NONE;
    protected int emphasizeColor = 0xFFFFFFFF;

    public EmphasizeWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderEmphasize(guiGraphics);
    }

    @Override
    public LayoutLocation getEmphasizeLocation() {
        return this.emphasizeLocation;
    }

    @Override
    public void setEmphasizeLocation(LayoutLocation location) {
        this.emphasizeLocation = location;
    }

    @Override
    public Emphasize getEmphasize() {
        return this.emphasize;
    }

    @Override
    public void setEmphasize(Emphasize emphasize) {
        this.emphasize = emphasize;
    }

    @Override
    public int getEmphasizeColor() {
        return emphasizeColor;
    }

    @Override
    public void setEmphasizeColor(int color) {
        emphasizeColor = color;
    }


    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
