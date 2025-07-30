package org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeEntryWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.ActionData;

/**
 * WheelTreeEntryWidget 是一个用于表示轮盘树条目的小部件类。
 * 它继承自 TreeEntryWidget，并提供了与轮盘相关的渲染和交互功能。
 */
public class WheelTreeEntryWidget extends TreeEntryWidget<ActionData> {

    /** 起始角度，用于定义轮盘条目的起始位置。 */
    protected float startAngle = 0;

    /** 结束角度，用于定义轮盘条目的结束位置。 */
    protected float endAngle = 0;

    /** 内半径，用于定义轮盘条目的内边界。 */
    protected float innerRadius = 0;

    /** 外半径，用于定义轮盘条目的外边界。 */
    protected float outerRadius = 0;

    /** 轮盘条目的中心 X 坐标。 */
    protected int centerX = 0;

    /** 轮盘条目的中心 Y 坐标。 */
    protected int centerY = 0;

    /** 当前条目的层级，用于区分嵌套的轮盘条目。 */
    protected int layer = 0;

    /**
     * 调整条目的大小，设置中心点为屏幕中心。
     */
    public void resize() {
        this.centerX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
        this.centerY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;
    }

    /**
     * 设置条目的层级。
     *
     * @param layer 层级值
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * 设置条目的起始角度和结束角度。
     *
     * @param startAngle 起始角度
     * @param endAngle   结束角度
     */
    public void setAngle(float startAngle, float endAngle) {
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    /**
     * 设置条目的内半径和外半径。
     *
     * @param innerRadius 内半径
     * @param outerRadius 外半径
     */
    public void setRadius(float innerRadius, float outerRadius) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    /**
     * 构造函数，初始化条目并设置消息和字体。
     *
     * @param message 条目的显示消息
     * @param font    用于渲染的字体
     */
    public WheelTreeEntryWidget(Component message, Font font) {
        super(message, font);
        resize();
    }

    /**
     * 创建一个新的 WheelTreeEntryWidget 实例。
     *
     * @param data      条目的动作数据
     * @param component 条目的显示消息
     * @param font      用于渲染的字体
     * @return 创建的 WheelTreeEntryWidget 实例
     */
    public static WheelTreeEntryWidget create(ActionData data, Component component, Font font) {
        WheelTreeEntryWidget widget = new WheelTreeEntryWidget(component, font);
        widget.setData(data);
        return widget;
    }

    /**
     * 渲染条目小部件。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的附加参数
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        IUIGuiUtils.drawRing(guiGraphics, centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, 0x80FFFFFF);
        if (isHovered(mouseX, mouseY)) {
            IUIGuiUtils.drawRing(guiGraphics, centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, 0xD0DDDDDD);
        }

        float midAngle = (startAngle + endAngle) / 2 - 90;
        float midRadius = (innerRadius + outerRadius) / 2;
        IUIGuiUtils.drawCenteredString(guiGraphics, font, getMessage(),
                (centerX + (float) Math.cos(Math.toRadians(midAngle)) * midRadius),
                (centerY + (float) Math.sin(Math.toRadians(midAngle)) * midRadius), 0xFFFFFFFF, false);

        if (fold) return;
        renderChildren(guiGraphics, mouseX, mouseY, v);
    }

    /**
     *  不允许接受子节点
     */
    @Override
    public boolean shouldAccept(double mouseX, double mouseY) {
        return false;
    }

    /**
     * 更新条目小部件的状态。
     * 计算子条目的角度、半径和层级，并递归更新子条目。
     */
    @Override
    public void updateWidget() {
        int i = 0;
        int size = children.size();
        for (TreeEntryWidget<ActionData> child : children) {
            if (child instanceof WheelTreeEntryWidget widget) {
                widget.setAngle(i * 360.0f / size, (i + 1) * 360.0f / size);
                widget.setRadius(outerRadius + 5, outerRadius + 30 - layer * 10);
                widget.setLayer(layer + 1);
                widget.updateWidget();
            }
            i++;
        }
    }

    /**
     * 判断鼠标是否悬停在条目上。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @return 如果鼠标悬停在条目上则返回 true，否则返回 false
     */
    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        // 将鼠标坐标转换为相对于中心的坐标
        double x = mouseX - centerX;
        double y = mouseY - centerY;
        // 转换为极坐标
        double r = Math.sqrt(x * x + y * y);
        double angle = Math.toDegrees(Math.atan2(y, x)) + 90;
        if (angle < 0) {
            angle += 360;
        } else if (angle > 360) {
            angle -= 360;
        }
        if (startAngle < 0 && endAngle > 0) {
            boolean left = angle > startAngle + 360 && angle < 360;
            boolean reght = angle < endAngle && angle > 0;
            return r >= (double) this.innerRadius && r <= (double) this.outerRadius && (left || reght);
        }
        if (startAngle < 0 && endAngle < 0) {
            startAngle += 360;
            endAngle += 360;
        }
        return r >= innerRadius && r <= outerRadius && angle >= startAngle && angle <= endAngle;
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
        if (this.hasChild()) {
            if (isHovered(mouseX, mouseY) && button == 0) {
                if (this.fold) {
                    this.unfold();
                } else {
                    this.fold();
                }
                return true;
            }
            TreeEntryWidget<ActionData> child = this.getWidgetAt(mouseX, mouseY);
            if (child != null) {
                boolean v = child.mouseClicked(mouseX, mouseY, button);
                this.updateWidget();
                return v;
            }
        } else if (button == 0 && getData() != null) {
            return false;
        }

        return true;
    }
}