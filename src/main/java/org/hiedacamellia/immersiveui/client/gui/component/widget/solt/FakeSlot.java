package org.hiedacamellia.immersiveui.client.gui.component.widget.solt;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;

public class FakeSlot extends AbstractWidget {
    private static final Font font = Minecraft.getInstance().font;

    public FakeSlot(int x, int y, Component tooltip) {
        super(x, y, 16, 16, tooltip);
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        IUIGuiUtils.renderSlotBackground(guiGraphics, getX(), getY());
        RenderSystem.enableBlend();
        if(isHovered()&&!getMessage().equals(Component.empty())){
            int w = font.width(getMessage());
            int h = font.lineHeight;
            int x = getX() - w;
            int y = getY() - h;
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            pose.translate(0,0,500);
            IUIGuiUtils.fillRoundRect(guiGraphics, x - 1, y - 1, w + 1, h + 1, 0.2f, 0x80DDDDDD);
            guiGraphics.drawString(font, getMessage(), x, y, 0xFFFFFF);
            pose.popPose();
        }
        RenderSystem.disableBlend();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
