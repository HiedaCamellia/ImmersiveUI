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
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtils;

/**
 * FakeSlot 是一个自定义的 GUI 组件，表示一个虚拟的物品槽。
 * 它支持渲染背景和提示信息，并提供基本的交互功能。
 */
public class FakeSlot extends AbstractWidget {
    private static final Font font = IUIMinecraftUtils.getFont(); // 字体对象，用于渲染文本

    /**
     * 设置是否渲染背景。
     * 如果为 true，则渲染背景；如果为 false，则不渲染背景。
     *
     * @param renderBackground 是否渲染背景
     */
    public void setRenderBackground(boolean renderBackground) {
        this.renderBackground = renderBackground;
    }

    /**
     * 设置是否渲染提示信息。
     * 如果为 true，则渲染提示信息；如果为 false，则不渲染提示信息。
     *
     * @param renderInfo 是否渲染提示信息
     */
    public void setRenderInfo(boolean renderInfo) {
        this.renderInfo = renderInfo;
    }

    protected boolean renderBackground = true; // 是否渲染背景
    protected boolean renderInfo = true; // 是否渲染提示信息

    /**
     * 构造一个 FakeSlot 实例。
     *
     * @param x       物品槽的 X 坐标
     * @param y       物品槽的 Y 坐标
     * @param tooltip 提示信息
     */
    public FakeSlot(int x, int y, Component tooltip) {
        super(x, y, 16, 16, tooltip);
    }

    /**
     * 检查是否点击了物品槽。
     * 当前实现始终返回 false。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 始终返回 false
     */
    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return false;
    }

    /**
     * 渲染物品槽组件，包括背景和提示信息。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (renderBackground) {
            IUIGuiUtils.renderSlotBackground(guiGraphics, getX(), getY());
        }

        if (renderInfo) {
            RenderSystem.enableBlend();
            if (isHovered() && !getMessage().equals(Component.empty())) {
                int w = font.width(getMessage());
                int h = font.lineHeight;
                int x = getX() - w;
                int y = getY() - h;
                PoseStack pose = guiGraphics.pose();
                pose.pushPose();
                pose.translate(0, 0, 500);
                IUIGuiUtils.fillRoundRect(guiGraphics, x - 1, y - 1, w + 1, h + 1, 0.2f, 0x80DDDDDD);
                guiGraphics.drawString(font, getMessage(), x, y, 0xFFFFFF);
                pose.popPose();
            }
            RenderSystem.disableBlend();
        }
    }

    /**
     * 更新组件的旁白信息。
     * 当前实现未定义任何旁白逻辑。
     *
     * @param narrationElementOutput 旁白输出对象
     */
    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        // 未实现旁白更新逻辑
    }
}