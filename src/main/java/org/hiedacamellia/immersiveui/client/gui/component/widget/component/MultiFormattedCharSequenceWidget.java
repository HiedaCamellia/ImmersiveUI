package org.hiedacamellia.immersiveui.client.gui.component.widget.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class MultiFormattedCharSequenceWidget extends AbstractStringWidget {

    private static final Font font = Minecraft.getInstance().font;
    protected FormattedCharSequence[] component;
    private boolean shadow = true;

    public static MultiFormattedCharSequenceWidget from(Component component, int width) {
        return new ComponentWidget(component).toMultiFormatted(width);
    }

    public MultiFormattedCharSequenceWidget(FormattedCharSequence... component) {
        this(0, 0, component);
    }

    public MultiFormattedCharSequenceWidget(int x, int y, int width, List<FormattedCharSequence> component) {
        this(x, y, width, component.toArray(new FormattedCharSequence[0]));
    }

    public MultiFormattedCharSequenceWidget(int x, int y, List<FormattedCharSequence> component) {
        this(x, y, component.toArray(new FormattedCharSequence[0]));
    }

    public MultiFormattedCharSequenceWidget(int x, int y, FormattedCharSequence... component) {
        this(x, y, componentWidth(component), component);
    }

    public MultiFormattedCharSequenceWidget(int x, int y, int width, FormattedCharSequence... component) {
        super(x, y, width, font.lineHeight * component.length, Component.empty(),font);
        this.component = component;
    }

    public MultiFormattedCharSequenceWidget setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    private static int componentWidth(FormattedCharSequence[] component) {
        int width = 0;
        for (FormattedCharSequence c : component) {
            if (font.width(c) > width) {
                width = font.width(c);
            }
        }
        return width;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int y = this.getY();
        for (FormattedCharSequence c : component) {
            guiGraphics.drawString(font, c, this.getX(), y, getColor(), shadow);
            y += font.lineHeight;
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
