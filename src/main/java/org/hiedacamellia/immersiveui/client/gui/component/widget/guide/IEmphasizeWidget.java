package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.ILayoutExtension;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.LayoutLocation;
import org.joml.Vector2f;

/**
 * 支持强调效果的控件接口。
 */
public interface IEmphasizeWidget extends ILayoutExtension {

    /**
     * 强调样式枚举。
     */
    enum Emphasize {
        NONE,
        LINE_UNDER,
        LINE_OVER,
        BOLD
    }

    /**
     * 获取强调位置。
     */
    LayoutLocation getEmphasizeLocation();

    /**
     * 设置强调位置。
     */
    void setEmphasizeLocation(LayoutLocation location);

    /**
     * 获取强调样式。
     */
    Emphasize getEmphasize();

    /**
     * 设置强调样式。
     */
    void setEmphasize(Emphasize emphasize);

    /**
     * 获取强调颜色。
     */
    int getEmphasizeColor();

    /**
     * 设置强调颜色。
     */
    void setEmphasizeColor(int color);

    /**
     * 获取强调线的起点坐标。
     */
    default Vector2f getOuterLineStart(){
        return getLocationPoint(getEmphasizeLocation());
    }

    /**
     * 渲染强调效果。
     */
    default void renderEmphasize(GuiGraphics guiGraphics) {
        EmphasizeRenderHelper.renderEmphasize(this, guiGraphics, getEmphasizeColor(), getEmphasize());
    }

}
