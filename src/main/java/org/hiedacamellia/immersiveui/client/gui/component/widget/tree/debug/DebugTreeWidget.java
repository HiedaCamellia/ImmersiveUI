package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.debug.DebugEntry;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeWidget;

import java.util.List;

/**
 * DebugTreeWidget 是一个自定义的树形组件，用于debug页面。
 * 它继承自 TreeWidget，并对拖拽功能、标题渲染和背景渲染进行了定制。
 */
public class DebugTreeWidget extends TreeWidget<DebugEntry, DebugTreeEntryWidget> {

    /**
     * 构造一个 DebugTreeWidget 实例。
     *
     * @param root      根节点列表
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 标题文本
     * @param font      用于渲染文本的字体
     */
    public DebugTreeWidget(List<DebugTreeEntryWidget> root, int x, int y, Component component, Font font) {
        super(root, x, y, component, font);
        this.dragable = false; // 禁用拖拽功能
        this.titleHeight = 20; // 设置标题高度
        this.titleWidth += 40; // 增加标题宽度
        updateWidget(); // 更新组件布局
    }

    /**
     * 创建一个 DebugTreeWidget 实例。
     *
     * @param root      根节点列表
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 标题文本
     * @param font      用于渲染文本的字体
     * @return 创建的 DebugTreeWidget 实例
     */
    public static DebugTreeWidget create(List<DebugTreeEntryWidget> root, int x, int y, Component component, Font font) {
        return new DebugTreeWidget(root, x, y, component, font);
    }

    /**
     * 处理鼠标点击事件。
     * 如果选中的节点没有子节点，则切换到该节点关联的调试界面。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param button 鼠标按键
     * @return 如果事件被处理返回 true，否则返回 false
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean v = super.mouseClicked(mouseX, mouseY, button);
        if (select != null && !select.hasChild()) {
            DebugEntry data = select.getData();
            Minecraft.getInstance().setScreen(data.subScreen()); // 切换到子屏幕
            return true;
        }
        return v;
    }

    /**
     * 渲染组件的标题。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    protected void renderTitle(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        RenderSystem.enableBlend(); // 启用混合模式
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + titleHeight, 0x80AAAAAA); // 绘制标题背景
        IUIGuiUtils.drawCenteredString(guiGraphics, font, this.getMessage(), this.getX() + this.width / 2, this.getY() + titleHeight / 2, 0x000000, false); // 绘制标题文本
        RenderSystem.disableBlend(); // 禁用混合模式
    }

    /**
     * 渲染组件的背景。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        RenderSystem.enableBlend(); // 启用混合模式
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0x80DDDDDD); // 绘制背景
        RenderSystem.disableBlend(); // 禁用混合模式
    }
}