package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector3f;

import java.util.UUID;

/**
 * IW2SWidget 接口定义了一个用于屏幕空间渲染的组件。
 * 提供了与组件的状态、位置、渲染和交互相关的方法。
 */
public interface IW2SWidget {

    /**
     * 获取组件的唯一标识符。
     *
     * @return 组件的 UUID
     */
    UUID getId();

    /**
     * 检查组件是否已计算。
     *
     * @return 如果已计算则返回 true，否则返回 false
     */
    boolean isComputed();

    /**
     * 检查组件是否在屏幕内。
     *
     * @return 如果在屏幕内则返回 true，否则返回 false
     */
    boolean isInScreen();

    /**
     * 检查组件是否可被选中。
     *
     * @return 如果可选中则返回 true，否则返回 false
     */
    boolean isSelectable();

    /**
     * 检查组件是否应该渲染。
     *
     * @return 如果应该渲染则返回 true，否则返回 false
     */
    boolean shouldRender();

    /**
     * 检查组件是否应该被移除。
     *
     * @return 如果应该被移除则返回 true，否则返回 false
     */
    boolean shouldBeRemoved();

    /**
     * 检查组件是否标记为移除。
     *
     * @return 如果标记为移除则返回 true，否则返回 false
     */
    boolean shouldRemove();

    /**
     * 检查组件是否应该平滑位置变化。
     *
     * @return 如果应该平滑位置变化则返回 true，否则返回 false
     */
    boolean shouldSmoothPosition();

    /**
     * 检查组件是否应该跳过某些处理。
     *
     * @return 如果应该跳过则返回 true，否则返回 false
     */
    boolean shouldSkip();

    /**
     * 处理鼠标点击事件。
     *
     * @param button 鼠标按键
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    boolean click(int button);

    /**
     * 处理鼠标滚动事件。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param scrollX 滚动的 X 方向增量
     * @param scrollY 滚动的 Y 方向增量
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    boolean scroll(double mouseX, double mouseY, double scrollX, double scrollY);

    /**
     * 限制组件在屏幕范围内。
     *
     * @return 如果限制成功则返回 true，否则返回 false
     */
    boolean limitInScreen();

    /**
     * 调整组件的大小。
     */
    void resize();

    /**
     * 根据距离计算渲染缩放比例。
     *
     * @param distance 距离值
     */
    void calculateRenderScale(float distance);

    /**
     * 更新组件的透明度。
     */
    void updateAlpha();

    /**
     * 获取组件的世界坐标。
     *
     * @param out 用于存储世界坐标的 Vector3f 对象
     */
    void getWorldPos(Vector3f out);

    /**
     * 标记组件为已移除。
     */
    void setRemoved();

    /**
     * 设置组件是否在屏幕内。
     *
     * @param inScreen 是否在屏幕内
     */
    void setInScreen(boolean inScreen);

    /**
     * 标记组件为已计算。
     */
    void setComputed();

    /**
     * 设置组件的屏幕坐标。
     *
     * @param x 屏幕 X 坐标
     * @param y 屏幕 Y 坐标
     */
    void setScreenPos(float x, float y);

    /**
     * 获取组件的屏幕 X 坐标。
     *
     * @return 屏幕 X 坐标
     */
    float getX();

    /**
     * 获取组件的屏幕 Y 坐标。
     *
     * @return 屏幕 Y 坐标
     */
    float getY();

    /**
     * 获取组件的屏幕 X 偏移量。
     *
     * @return 屏幕 X 偏移量
     */
    float getXO();

    /**
     * 获取组件的屏幕 Y 偏移量。
     *
     * @return 屏幕 Y 偏移量
     */
    float getYO();

    /**
     * 获取组件的缩放比例。
     *
     * @return 缩放比例
     */
    float getScale();

    /**
     * 设置组件的屏幕 X 坐标。
     *
     * @param x 屏幕 X 坐标
     */
    void setX(float x);

    /**
     * 设置组件的屏幕 Y 坐标。
     *
     * @param y 屏幕 Y 坐标
     */
    void setY(float y);

    /**
     * 设置组件的屏幕 X 偏移量。
     *
     * @param xO 屏幕 X 偏移量
     */
    void setXO(float xO);

    /**
     * 设置组件的屏幕 Y 偏移量。
     *
     * @param yO 屏幕 Y 偏移量
     */
    void setYO(float yO);

    /**
     * 渲染组件。
     *
     * @param guiGraphics 渲染上下文
     * @param highlight 是否高亮显示
     * @param value 渲染附加值
     * @param deltaTracker 时间增量跟踪器
     */
    void render(GuiGraphics guiGraphics, boolean highlight, float value, DeltaTracker deltaTracker);

}