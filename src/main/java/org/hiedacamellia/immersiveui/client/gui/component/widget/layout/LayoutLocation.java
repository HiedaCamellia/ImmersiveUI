package org.hiedacamellia.immersiveui.client.gui.component.widget.layout;

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

    public boolean isTop() {
        return this == TOP || this == TOP_LEFT || this == TOP_RIGHT;
    }
    public boolean isBottom() {
        return this == BOTTOM || this == BOTTOM_LEFT || this == BOTTOM_RIGHT;
    }
    public boolean isLeft() {
        return this == LEFT || this == TOP_LEFT || this == BOTTOM_LEFT;
    }
    public boolean isRight() {
        return this == RIGHT || this == TOP_RIGHT || this == BOTTOM_RIGHT;
    }
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
    public LayoutLocation mirrorX() {
        return switch (this) {
            case TOP_LEFT -> TOP_RIGHT;
            case TOP_RIGHT -> TOP_LEFT;
            case BOTTOM_LEFT -> BOTTOM_RIGHT;
            case BOTTOM_RIGHT -> BOTTOM_LEFT;
            case TOP, BOTTOM, LEFT, RIGHT, CENTER, NONE -> this;
        };
    }
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
