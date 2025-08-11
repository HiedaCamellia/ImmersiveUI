package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import net.minecraft.client.gui.GuiGraphics;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.ILayoutExtension;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.LayoutLocation;
import org.joml.Vector2f;

public class EmphasizeRenderHelper {

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

    public static void renderEmphasize(float ratio, ILayoutExtension extension, GuiGraphics guiGraphics, int color, IEmphasizeWidget.Emphasize emphasize) {
        renderEmphasize(ratio, extension, extension.getLayoutLocation().mirrorX(), guiGraphics, color, emphasize);
    }
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

    public static void connect(ILayoutExtension fromLayout,ILayoutExtension toLayout,GuiGraphics guiGraphics,int color){
        connect(fromLayout, toLayout, fromLayout.getLayoutLocation(), toLayout.getLayoutLocation(), guiGraphics, color);
    }
    public static void connect(ILayoutExtension fromLayout,ILayoutExtension toLayout,LayoutLocation fromLoc,LayoutLocation toLoc,GuiGraphics guiGraphics,int color){
        Vector2f from = fromLayout.getLocationPoint(fromLoc);
        Vector2f to = toLayout.getLocationPoint(toLoc);
        IUIGuiUtils.hLine(guiGraphics,from.x,to.x,from.y,to.y,color);
    }
    public static void connect(float ratio,ILayoutExtension fromLayout,ILayoutExtension toLayout,GuiGraphics guiGraphics,int color){
        connect(ratio,fromLayout, toLayout, fromLayout.getLayoutLocation(), toLayout.getLayoutLocation(), guiGraphics, color);
    }
    public static void connect(float ratio,ILayoutExtension fromLayout,ILayoutExtension toLayout,LayoutLocation fromLoc,LayoutLocation toLoc,GuiGraphics guiGraphics,int color){
        if(ratio<=0)return;
        Vector2f from = fromLayout.getLocationPoint(fromLoc);
        Vector2f to = toLayout.getLocationPoint(toLoc);
        Vector2f offset = new Vector2f(to.x - from.x, to.y - from.y).mul(ratio);
        IUIGuiUtils.hLine(guiGraphics,from.x,from.x+offset.x,from.y,from.y+offset.y,color);
    }
    public static void connect(Vector2f from,Vector2f to,GuiGraphics guiGraphics,int color){
        IUIGuiUtils.hLine(guiGraphics,from.x,to.x,from.y,to.y,color);
    }
    public static void connectH(float ratio,Vector2f from,Vector2f to,GuiGraphics guiGraphics,int color){
        if(ratio<=0)return;
        Vector2f offset = new Vector2f(to.x - from.x, to.y - from.y).mul(ratio);
        IUIGuiUtils.hLine(guiGraphics,from.x,from.x+offset.x,from.y,from.y+offset.y,color);
    }
    public static void connectV(float ratio,Vector2f from,Vector2f to,GuiGraphics guiGraphics,int color){
        if(ratio<=0)return;
        Vector2f offset = new Vector2f(to.x - from.x, to.y - from.y).mul(ratio);
        IUIGuiUtils.vLine(guiGraphics,from.x,from.x+offset.x,from.y,from.y+offset.y,color);
    }
}
