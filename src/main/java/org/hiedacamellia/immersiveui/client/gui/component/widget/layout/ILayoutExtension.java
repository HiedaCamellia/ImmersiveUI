package org.hiedacamellia.immersiveui.client.gui.component.widget.layout;

import net.minecraft.client.gui.layouts.LayoutElement;
import org.joml.Vector2f;

/**
 * 提供布局元素的扩展接口，增加了便捷的几何属性和布局定位方法。
 */
public interface ILayoutExtension extends LayoutElement {

    /**
     * 获取元素顶部的Y坐标（float）。
     */
    default float getTopF(){
        return getY();
    }

    /**
     * 获取元素底部的Y坐标（float）。
     */
    default float getBottomF(){
        return getY() + getHeight();
    }

    /**
     * 获取元素左侧的X坐标（float）。
     */
    default float getLeftF(){
        return getX();
    }

    /**
     * 获取元素右侧的X坐标（float）。
     */
    default float getRightF(){
        return getX() + getWidth();
    }

    /**
     * 获取元素中心的X坐标（float）。
     */
    default float getCenterX(){
        return getX() + getWidth() / 2f;
    }

    /**
     * 获取元素中心的Y坐标（float）。
     */
    default float getCenterY(){
        return getY() + getHeight() / 2f;
    }

    /**
     * 获取元素中心点的坐标（Vector2f）。
     */
    default Vector2f getCenter(){
        return new Vector2f(getCenterX(), getCenterY());
    }

    /**
     * 获取当前布局定位点的坐标（Vector2f）。
     */
    default Vector2f getLocationPoint(){
        return getLocationPoint(getLayoutLocation());
    }

    /**
     * 根据指定布局定位获取对应的坐标点。
     * @param location 布局定位
     * @return 对应的坐标点
     */
    default Vector2f getLocationPoint(LayoutLocation location){
        return switch (location){
            case TOP -> new Vector2f(getCenterX(), getTopF());
            case BOTTOM -> new Vector2f(getCenterX(), getBottomF());
            case LEFT -> new Vector2f(getLeftF(), getCenterY());
            case RIGHT -> new Vector2f(getRightF(), getCenterY());
            case TOP_LEFT -> new Vector2f(getLeftF(), getTopF());
            case TOP_RIGHT -> new Vector2f(getRightF(), getTopF());
            case BOTTOM_LEFT -> new Vector2f(getLeftF(), getBottomF());
            case BOTTOM_RIGHT -> new Vector2f(getRightF(), getBottomF());
            case CENTER -> new Vector2f(getCenterX(), getCenterY());
            default -> getCenter();
        };
    }

    /**
     * 获取当前元素的布局定位，默认返回NONE。
     */
    default LayoutLocation getLayoutLocation(){
        return LayoutLocation.NONE;
    }

    /**
     * 设置当前元素的布局定位，默认无操作。
     */
    default void setLayoutLocation(LayoutLocation location){
    }
}
