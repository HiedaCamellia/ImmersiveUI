package org.hiedacamellia.immersiveui.client.graphic.layout.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.hiedacamellia.immersiveui.client.graphic.util.RenderUtils;

import java.util.List;

public class ComponentWidget extends Widget{

    private Component component;
    private Font font = Minecraft.getInstance().font;

    public ComponentWidget(Component component) {
        this.component = component;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        List<FormattedText> formattedTexts =font.getSplitter().splitLines(component, widgetWidth, Style.EMPTY);
        int o = 0;
        for (FormattedText formattedText : formattedTexts) {
            if(o+font.lineHeight>widgetHeight)break;
            guiGraphics.drawString(font,formattedText.getString(), (int) widgetX, (int) (widgetY + o), 0xFFFFFF);
            o+=font.lineHeight;
        }
    }


}
