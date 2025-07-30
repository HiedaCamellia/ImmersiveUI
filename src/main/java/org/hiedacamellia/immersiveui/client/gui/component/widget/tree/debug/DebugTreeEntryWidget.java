package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.debug.DebugEntry;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeEntryWidget;

/**
 * DebugTreeEntryWidget 是一个自定义的树形节点组件，用于debug页面。
 * 它继承自 TreeEntryWidget，并对节点的渲染和行为进行了定制。
 */
public class DebugTreeEntryWidget extends TreeEntryWidget<DebugEntry> {

    /**
     * 构造一个 DebugTreeEntryWidget 实例。
     *
     * @param message 节点显示的消息
     * @param font    用于渲染文本的字体
     */
    public DebugTreeEntryWidget(Component message, Font font) {
        super(message, font);
        this.foldWidth = 20; // 设置折叠图标的宽度
        this.selfHeight = 20; // 设置节点的高度
        this.selfWidth += 40; // 增加节点的宽度
    }

    /**
     * 创建一个 DebugTreeEntryWidget 实例。
     *
     * @param data      节点关联的调试数据
     * @param component 节点显示的消息
     * @param font      用于渲染文本的字体
     * @return 创建的 DebugTreeEntryWidget 实例
     */
    public static DebugTreeEntryWidget create(DebugEntry data, Component component, Font font) {
        DebugTreeEntryWidget widget = new DebugTreeEntryWidget(component, font);
        widget.setData(data); // 设置节点数据
        widget.updateWidget(); // 更新节点布局
        return widget;
    }

    /**
     * 渲染节点组件。
     * 包括折叠/展开图标、消息文本以及子节点的渲染。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (hasChild()) {
            // 渲染折叠/展开图标和消息文本
            IUIGuiUtils.drawCenteredString(guiGraphics, font, fold ? foldComponent : unfoldComponent, this.getX() + foldWidth / 2, this.getY() + selfHeight / 2, 0xFFFFFF, false);
            guiGraphics.drawString(font, getMessage().getVisualOrderText(), (float) this.getX() + foldWidth, (float) this.getY() + (float) selfHeight / 2 - (float) font.lineHeight / 2, 0xFFFFFF, false);
        } else {
            // 渲染没有子节点的消息文本
            guiGraphics.drawString(font, getMessage().getVisualOrderText(), (float) this.getX(), (float) this.getY() + (float) selfHeight / 2 - (float) font.lineHeight / 2, 0xFFFFFF, false);
        }

        if (fold) return; // 如果节点是折叠状态，则不渲染子节点
        renderChildren(guiGraphics, mouseX, mouseY, v); // 渲染子节点
    }

    /**
     *  不允许接受子节点
     */
    @Override
    public boolean shouldAccept(double mouseX, double mouseY) {
        return false;
    }
}