package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.LayoutLocation;

/**
 * 带有强调效果的基础控件抽象类。
 */
public abstract class EmphasizeWidget extends AbstractWidget implements IEmphasizeWidget {

    protected LayoutLocation emphasizeLocation = LayoutLocation.NONE;
    protected Emphasize emphasize = Emphasize.NONE;
    protected int emphasizeColor = 0xFFFFFFFF;

    /**
     * 构造一个EmphasizeWidget。
     * @param x X坐标
     * @param y Y坐标
     * @param width 宽度
     * @param height 高度
     * @param message 显示内容
     */
    public EmphasizeWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    /**
     * 渲染强调效果。
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderEmphasize(guiGraphics);
    }

    /**
     * 获取强调位置。
     */
    @Override
    public LayoutLocation getEmphasizeLocation() {
        return this.emphasizeLocation;
    }

    /**
     * 设置强调位置。
     */
    @Override
    public void setEmphasizeLocation(LayoutLocation location) {
        this.emphasizeLocation = location;
    }

    /**
     * 获取强调样式。
     */
    @Override
    public Emphasize getEmphasize() {
        return this.emphasize;
    }

    /**
     * 设置强调样式。
     */
    @Override
    public void setEmphasize(Emphasize emphasize) {
        this.emphasize = emphasize;
    }

    /**
     * 获取强调颜色。
     */
    @Override
    public int getEmphasizeColor() {
        return emphasizeColor;
    }

    /**
     * 设置强调颜色。
     */
    @Override
    public void setEmphasizeColor(int color) {
        emphasizeColor = color;
    }

    /**
     * Narration（无实现）。
     */
    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
