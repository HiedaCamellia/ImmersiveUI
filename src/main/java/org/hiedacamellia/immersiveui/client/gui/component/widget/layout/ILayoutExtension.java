package org.hiedacamellia.immersiveui.client.gui.component.widget.layout;

import net.minecraft.client.gui.layouts.LayoutElement;
import org.joml.Vector2f;

public interface ILayoutExtension extends LayoutElement {

    default float getTopF(){
        return getY();
    }

    default float getBottomF(){
        return getY() + getHeight();
    }

    default float getLeftF(){
        return getX();
    }

    default float getRightF(){
        return getX() + getWidth();
    }

    default float getCenterX(){
        return getX() + getWidth() / 2f;
    }

    default float getCenterY(){
        return getY() + getHeight() / 2f;
    }

    default Vector2f getCenter(){
        return new Vector2f(getCenterX(), getCenterY());
    }

    default Vector2f getLocationPoint(){
        return getLocationPoint(getLayoutLocation());
    }

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

    default LayoutLocation getLayoutLocation(){
        return LayoutLocation.NONE;
    }

    default void setLayoutLocation(LayoutLocation location){
    }
}
