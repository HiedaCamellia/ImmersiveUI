package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMathUtils;
import org.joml.Vector2f;

import java.util.UUID;

import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_BEGIN_DISTANCE;
import static org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer.FADE_DISTANCE;

/**
 * World2ScreenWidget 是一个抽象类，表示从世界坐标到屏幕坐标的可渲染组件。
 * 提供了组件的基本属性和操作方法，如位置、缩放、透明度等。
 */
@OnlyIn(Dist.CLIENT)
public abstract class World2ScreenWidget implements IW2SWidget {

    /** 屏幕 X 偏移量。 */
    public float xO;

    /** 屏幕 X 坐标。 */
    public float x;

    /** 屏幕 Y 偏移量。 */
    public float yO;

    /** 屏幕 Y 坐标。 */
    public float y;

    /** 组件的缩放比例。 */
    public float scale;

    /** 是否可选中。 */
    public boolean selectable = false;

    /** 是否限制在屏幕范围内。 */
    protected boolean limitInScreen = false;

    /** 是否平滑位置变化。 */
    protected boolean smoothPosition = false;

    /** 是否已计算。 */
    private boolean computed = false;

    /** 是否在屏幕内。 */
    private boolean inScreen = false;

    /** 是否标记为移除。 */
    protected boolean shouldRemove = false;

    /** 组件的透明度。 */
    protected float alpha = 0;

    /** 组件的唯一标识符。 */
    protected UUID uuid;

    /**
     * 构造函数，初始化组件的唯一标识符。
     *
     * @param uuid 组件的 UUID
     */
    protected World2ScreenWidget(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void setScreenPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void calculateRenderScale(float distance) {
        this.scale = IUIMathUtils.smoothLerp(0, 1, 1.0f - Math.max(distance - FADE_BEGIN_DISTANCE, 0) / FADE_DISTANCE);
    }

    /**
     * 调整组件的大小。
     */
    public void resize() {}

    /**
     * 调用组件的特定行为。
     */
    public void invoke() {}

    @Override
    public void updateAlpha() {
        if (shouldRemove)
            alpha -= 0.05f;
        else if (alpha < 1.0f)
            alpha += 0.05f;
    }

    @Override
    public boolean shouldBeRemoved() {
        return alpha <= 0.0f;
    }

    @Override
    public boolean shouldRemove() {
        return this.shouldRemove;
    }

    @Override
    public void setRemoved() {
        this.shouldRemove = true;
    }

    /**
     * 检查当前组件是否与另一个组件发生碰撞。
     *
     * @param onGrid 另一个组件
     * @return 如果发生碰撞则返回 true，否则返回 false
     */
    public boolean collided(World2ScreenWidget onGrid) {
        return Vector2f.distanceSquared(this.x, this.y, onGrid.x, onGrid.y) < 32 * 32;
    }

    /**
     * 如果与另一个组件重叠，则移动当前组件以避免重叠。
     *
     * @param other 另一个组件
     */
    public void moveIfOverlapped(World2ScreenWidget other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        float distance = Mth.sqrt(dx * dx + dy * dy);

        if (distance < 32) {
            float moveDistance = 32 - distance;
            float moveX = (moveDistance / distance) * dx;
            float moveY = (moveDistance / distance) * dy;

            this.x += moveX;
            this.y += moveY;
        }
    }

    @Override
    public void setComputed() {
        this.computed = true;
    }

    @Override
    public boolean isComputed() {
        return computed;
    }

    @Override
    public boolean limitInScreen() {
        return this.limitInScreen;
    }

    @Override
    public boolean shouldRender() {
        return this.inScreen && this.scale > 0f;
    }

    @Override
    public boolean shouldSmoothPosition() {
        return this.smoothPosition;
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    @Override
    public void setInScreen(boolean inScreen) {
        this.inScreen = inScreen;
    }

    @Override
    public boolean isInScreen() {
        return inScreen;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getXO() {
        return xO;
    }

    @Override
    public float getYO() {
        return yO;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setXO(float xO) {
        this.xO = xO;
    }

    @Override
    public void setYO(float yO) {
        this.yO = yO;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public boolean isSelectable() {
        return selectable;
    }
}