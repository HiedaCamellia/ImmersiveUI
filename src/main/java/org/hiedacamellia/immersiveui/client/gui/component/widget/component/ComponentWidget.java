package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;

public class ComponentWidget extends AbstractStringWidget {

    protected static final Font font = IUIGuiUtils.getFont();
    protected Component component;
    protected boolean shadow = true;

    public ComponentWidget(Component component) {
        this(0, 0, component);
    }

    public ComponentWidget(int x, int y, Component component) {
        super(x, y, font.width(component), font.lineHeight, component,font);
        this.component = component;
    }

    public ComponentWidget(int x, int y, int w, int h, Component component) {
        super(x, y, w, h, component,font);
    }

    public ComponentWidget setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawString(font, component, this.getX(), this.getY(), getColor(), shadow);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public MultiFormattedCharSequenceWidget toMultiFormatted(int width) {
        return new MultiFormattedCharSequenceWidget(this.getX(), this.getY(), width, font.split(component, width)).setShadow(this.shadow);
    }

}
