package org.hiedacamellia.immersiveui.client.gui.component.widget.layout;

/**
 * 表示布局元素在父容器中的定位。
 */
public enum LayoutLocation {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    CENTER,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    NONE;

    /**
     * 判断是否为顶部相关位置。
     */
    public boolean isTop() {
        return this == TOP || this == TOP_LEFT || this == TOP_RIGHT;
    }

    /**
     * 判断是否为底部相关位置。
     */
    public boolean isBottom() {
        return this == BOTTOM || this == BOTTOM_LEFT || this == BOTTOM_RIGHT;
    }

    /**
     * 判断是否为左侧相关位置。
     */
    public boolean isLeft() {
        return this == LEFT || this == TOP_LEFT || this == BOTTOM_LEFT;
    }

    /**
     * 判断是否为右侧相关位置。
     */
    public boolean isRight() {
        return this == RIGHT || this == TOP_RIGHT || this == BOTTOM_RIGHT;
    }

    /**
     * 获取当前定位的对侧定位。
     * @return 对侧定位
     */
    public LayoutLocation opposite() {
        return switch (this) {
            case TOP -> BOTTOM;
            case BOTTOM -> TOP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case TOP_LEFT -> BOTTOM_RIGHT;
            case TOP_RIGHT -> BOTTOM_LEFT;
            case BOTTOM_LEFT -> TOP_RIGHT;
            case BOTTOM_RIGHT -> TOP_LEFT;
            case CENTER, NONE -> this;
        };
    }

    /**
     * 沿X轴镜像定位。
     * @return 镜像后的定位
     */
    public LayoutLocation mirrorX() {
        return switch (this) {
            case TOP_LEFT -> TOP_RIGHT;
            case TOP_RIGHT -> TOP_LEFT;
            case BOTTOM_LEFT -> BOTTOM_RIGHT;
            case BOTTOM_RIGHT -> BOTTOM_LEFT;
            case TOP, BOTTOM, LEFT, RIGHT, CENTER, NONE -> this;
        };
    }

    /**
     * 沿Y轴镜像定位。
     * @return 镜像后的定位
     */
    public LayoutLocation mirrorY() {
        return switch (this) {
            case TOP_LEFT -> BOTTOM_LEFT;
            case TOP_RIGHT -> BOTTOM_RIGHT;
            case BOTTOM_LEFT -> TOP_LEFT;
            case BOTTOM_RIGHT -> TOP_RIGHT;
            case TOP, BOTTOM, LEFT, RIGHT, CENTER, NONE -> this;
        };
    }
}
