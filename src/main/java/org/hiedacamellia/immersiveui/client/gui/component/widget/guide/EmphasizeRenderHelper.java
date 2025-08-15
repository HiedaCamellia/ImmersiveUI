package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.ILayoutExtension;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.LayoutLocation;
import org.joml.Vector2f;

/**
 * 提供强调效果和连线的渲染辅助方法。
 */
public class EmphasizeRenderHelper {

    /**
     * 渲染强调效果（无动画）。
     */
    public static void renderEmphasize(ILayoutExtension extension, GuiGraphics guiGraphics, int color, IEmphasizeWidget.Emphasize emphasize) {
        switch (emphasize) {
            case BOLD:
                IUIGuiUtils.vLine(guiGraphics, extension.getLeftF(), extension.getTopF() + 1, extension.getBottomF() - 1, color);
                IUIGuiUtils.vLine(guiGraphics, extension.getRightF(), extension.getTopF() + 1, extension.getBottomF() - 1, color);
                IUIGuiUtils.hLine(guiGraphics, extension.getLeftF() + 1, extension.getRightF() - 1, extension.getTopF(), color);
                IUIGuiUtils.hLine(guiGraphics, extension.getLeftF() + 1, extension.getRightF() - 1, extension.getBottomF(), color);
                break;
            case LINE_UNDER:
                IUIGuiUtils.hLine(guiGraphics, extension.getLeftF(), extension.getRightF(), extension.getBottomF(), color);
                break;
            case LINE_OVER:
                IUIGuiUtils.hLine(guiGraphics, extension.getLeftF(), extension.getRightF(), extension.getTopF(), color);
                break;
        }
    }

    /**
     * 渲染强调效果（带动画，自动取镜像布局）。
     */
    public static void renderEmphasize(float ratio, ILayoutExtension extension, GuiGraphics guiGraphics, int color, IEmphasizeWidget.Emphasize emphasize) {
        renderEmphasize(ratio, extension, extension.getLayoutLocation().mirrorX(), guiGraphics, color, emphasize);
    }

    /**
     * 渲染强调效果（带动画，指定布局端点）。
     */
    public static void renderEmphasize(float ratio,  ILayoutExtension extension,LayoutLocation end, GuiGraphics guiGraphics, int color, IEmphasizeWidget.Emphasize emphasize) {
        switch (emphasize) {
            case BOLD:
                IUIGuiUtils.vLine(guiGraphics, extension.getLeftF(), extension.getTopF() + 1, extension.getBottomF() - 1, color);
                IUIGuiUtils.vLine(guiGraphics, extension.getRightF(), extension.getTopF() + 1, extension.getBottomF() - 1, color);
                IUIGuiUtils.hLine(guiGraphics, extension.getLeftF() + 1, extension.getRightF() - 1, extension.getTopF(), color);
                IUIGuiUtils.hLine(guiGraphics, extension.getLeftF() + 1, extension.getRightF() - 1, extension.getBottomF(), color);
                break;
            case LINE_UNDER:
                if (end.isBottom()) {
                    IUIGuiUtils.hLine(guiGraphics,
                            end.isLeft() ? extension.getLeftF() : extension.getRightF() - extension.getWidth() * ratio,
                            end.isLeft() ? extension.getLeftF() + extension.getWidth() * ratio : extension.getRightF(),
                            extension.getBottomF(), color);
                }
                break;
            case LINE_OVER:
                if (end.isTop()) {
                    IUIGuiUtils.hLine(guiGraphics,
                            end.isLeft() ? extension.getLeftF() : extension.getRightF() - extension.getWidth() * ratio,
                            end.isLeft() ? extension.getLeftF() + extension.getWidth() * ratio : extension.getRightF(),
                            extension.getTopF(), color);
                }
                break;
        }
    }

    /**
     * 直接连接两个布局元素。
     */
    public static void connect(ILayoutExtension fromLayout,ILayoutExtension toLayout,GuiGraphics guiGraphics,int color){
        connect(fromLayout, toLayout, fromLayout.getLayoutLocation(), toLayout.getLayoutLocation(), guiGraphics, color);
    }

    /**
     * 连接两个布局元素，指定端点。
     */
    public static void connect(ILayoutExtension fromLayout,ILayoutExtension toLayout,LayoutLocation fromLoc,LayoutLocation toLoc,GuiGraphics guiGraphics,int color){
        Vector2f from = fromLayout.getLocationPoint(fromLoc);
        Vector2f to = toLayout.getLocationPoint(toLoc);
        IUIGuiUtils.hLine(guiGraphics,from.x,to.x,from.y,to.y,color);
    }

    /**
     * 动画连接两个布局元素。
     */
    public static void connect(float ratio,ILayoutExtension fromLayout,ILayoutExtension toLayout,GuiGraphics guiGraphics,int color){
        connect(ratio,fromLayout, toLayout, fromLayout.getLayoutLocation(), toLayout.getLayoutLocation(), guiGraphics, color);
    }

    /**
     * 动画连接两个布局元素，指定端点。
     */
    public static void connect(float ratio,ILayoutExtension fromLayout,ILayoutExtension toLayout,LayoutLocation fromLoc,LayoutLocation toLoc,GuiGraphics guiGraphics,int color){
        if(ratio<=0)return;
        Vector2f from = fromLayout.getLocationPoint(fromLoc);
        Vector2f to = toLayout.getLocationPoint(toLoc);
        Vector2f offset = new Vector2f(to.x - from.x, to.y - from.y).mul(ratio);
        IUIGuiUtils.hLine(guiGraphics,from.x,from.x+offset.x,from.y,from.y+offset.y,color);
    }

    /**
     * 直接连接两个点。
     */
    public static void connect(Vector2f from,Vector2f to,GuiGraphics guiGraphics,int color){
        IUIGuiUtils.hLine(guiGraphics,from.x,to.x,from.y,to.y,color);
    }

    /**
     * 动画连接两个点（水平线）。
     */
    public static void connectH(float ratio,Vector2f from,Vector2f to,GuiGraphics guiGraphics,int color){
        if(ratio<=0)return;
        Vector2f offset = new Vector2f(to.x - from.x, to.y - from.y).mul(ratio);
        IUIGuiUtils.hLine(guiGraphics,from.x,from.x+offset.x,from.y,from.y+offset.y,color);
    }

    /**
     * 动画连接两个点（垂直线）。
     */
    public static void connectV(float ratio,Vector2f from,Vector2f to,GuiGraphics guiGraphics,int color){
        if(ratio<=0)return;
        Vector2f offset = new Vector2f(to.x - from.x, to.y - from.y).mul(ratio);
        IUIGuiUtils.vLine(guiGraphics,from.x,from.x+offset.x,from.y,from.y+offset.y,color);
    }
}
