package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeEntryWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.ActionData;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.WheelActionRegistries;

import java.util.List;

/**
 * WheelTreeWidget 是一个用于表示轮盘树的组件类。
 * 它继承自 TreeWidget，并提供了与轮盘相关的渲染和交互功能。
 */
public class WheelTreeWidget extends TreeWidget<ActionData, WheelTreeEntryWidget> {

    /** 轮盘的中心 X 坐标。 */
    protected int centerX = 0;

    /** 轮盘的中心 Y 坐标。 */
    protected int centerY = 0;

    /** 角度偏移量，用于调整轮盘条目的起始角度。 */
    protected float angleOffset = 0.0f;

    /**
     * 设置角度偏移量并更新小部件。
     *
     * @param angleOffset 角度偏移量
     */
    public void setAngleOffset(float angleOffset) {
        this.angleOffset = angleOffset;
        updateWidget();
    }

    /**
     * 调整轮盘的大小，将中心点设置为屏幕中心。
     * 同时调整所有子条目的大小。
     */
    public void resize() {
        this.centerX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
        this.centerY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;
        this.width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        this.height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        for (WheelTreeEntryWidget child : root) {
            child.resize();
        }
    }

    /**
     * 构造函数，初始化轮盘树组件。
     *
     * @param root      根条目列表
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 显示的标题
     * @param font      用于渲染的字体
     */
    public WheelTreeWidget(List<? extends WheelTreeEntryWidget> root, int x, int y, Component component, Font font) {
        super(root, x, y, component, font);
        resize();
    }

    /**
     * 创建一个新的 WheelTreeWidget 实例。
     *
     * @param root      根条目列表
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param component 显示的标题
     * @param font      用于渲染的字体
     * @return 创建的 WheelTreeWidget 实例
     */
    public static WheelTreeWidget create(List<? extends WheelTreeEntryWidget> root, int x, int y, Component component, Font font) {
        return new WheelTreeWidget(root, x, y, component, font);
    }

    /**
     * 更新轮盘树组件的状态。
     * 计算每个子条目的角度、半径和层级，并递归更新子条目。
     */
    @Override
    public void updateWidget() {
        int i = 0;
        int size = root.size();
        for (TreeEntryWidget<ActionData> child : root) {
            if (child instanceof WheelTreeEntryWidget widget) {
                widget.setAngle(i * 360.0f / size + angleOffset, (i + 1) * 360.0f / size + angleOffset);
                widget.setRadius(40, 75);
                widget.setLayer(0);
                widget.updateWidget();
            }
            i++;
        }
    }

    /**
     * 渲染轮盘树的标题。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的附加参数
     */
    @Override
    protected void renderTitle(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        IUIGuiUtils.drawCenteredString(guiGraphics, this.font, this.getMessage(), this.centerX, this.centerY, 0xFFFFFFFF, false);
    }

    /**
     * 渲染轮盘树的背景。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的附加参数
     */
    @Override
    protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        // 空实现，子类可重写
    }

    /**
     * 处理鼠标点击事件。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param button 鼠标按键
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean v = super.mouseClicked(mouseX, mouseY, button);
        if (select == null) {
            return false;
        }
        if (select.getData() != null)
            WheelActionRegistries.get(select.getData().resourceLocation()).accept(select.getData().compoundTag());
        return v;
    }

    /**
     * 获取鼠标所在位置的条目。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 鼠标所在位置的条目，如果未找到则返回 null
     */
    @Override
    public TreeEntryWidget<ActionData> getAt(double mouseX, double mouseY) {
        for (TreeEntryWidget<ActionData> child : this.root) {
            TreeEntryWidget<ActionData> widget = child.getAt(mouseX, mouseY);
            if (widget != null && widget.isHovered(mouseX, mouseY)) {
                return widget;
            }
        }
        return null;
    }
}