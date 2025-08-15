package org.hiedacamellia.immersiveui.client.gui.component.widget.guide;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.animate.AnimateContainer;
import org.hiedacamellia.immersiveui.client.gui.component.widget.layout.ILayoutExtension;
import org.joml.Vector2f;

/**
 * 支持与目标组件连线和强调动画的描述控件。
 */
public class EmphasizeDescriptionWidget extends EmphasizeComponentWidget {

    /** 目标组件强调动画容器 */
    protected AnimateContainer targetWidgetAnim = new AnimateContainer(500);
    /** 连线动画容器 */
    protected AnimateContainer connectAnim = new AnimateContainer(500);
    /** 自身强调动画容器 */
    protected AnimateContainer selfAnim = new AnimateContainer(500);

    /** 目标组件 */
    protected ILayoutExtension targetWidget;
    /** 目标位置（无目标组件时使用） */
    protected Vector2f targetPos = new Vector2f(0,0);
    /** 目标组件强调样式 */
    protected Emphasize targetEmphasize = Emphasize.LINE_UNDER;

    /** 自身强调样式 */
    protected Emphasize selfEmphasize = Emphasize.LINE_UNDER;

    /** 是否显示动画 */
    protected boolean show = false;

    /**
     * 设置目标组件。
     */
    public void setTargetWidget(ILayoutExtension targetWidget) {
        this.targetWidget = targetWidget;
    }

    /**
     * 设置目标组件强调样式。
     */
    public void setTargetEmphasize(Emphasize targetEmphasize) {
        this.targetEmphasize = targetEmphasize;
    }

    /**
     * 设置自身强调样式。
     */
    public void setSelfEmphasize(Emphasize selfEmphasize) {
        this.selfEmphasize = selfEmphasize;
    }

    /**
     * 设置目标位置（无目标组件时使用）。
     */
    public void setTargetPos(Vector2f targetPos) {
        this.targetPos = targetPos;
    }

    /**
     * 构造一个EmphasizeDescriptionWidget。
     * @param x X坐标
     * @param y Y坐标
     * @param message 显示的文本内容
     */
    public EmphasizeDescriptionWidget(int x, int y, Component message) {
        super(x, y, message);
    }

    /**
     * 播放显示动画。
     */
    public void show() {
        show = true;
        if(targetWidget!=null) {
            targetWidgetAnim.startAnimation();
            connectAnim.startAnimationAfter(500);
            selfAnim.startAnimationAfter(1000);
        }else {
            connectAnim.startAnimation();
            selfAnim.startAnimationAfter(500);
        }
    }

    /**
     * 播放隐藏动画。
     */
    public void hide() {
        show = false;
        selfAnim.startAnimation();
        connectAnim.startAnimationAfter(500);
        if(targetWidget!=null)
            targetWidgetAnim.startAnimationAfter(1000);
    }

    /**
     * 渲染强调动画、连线和文本内容。
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        if(targetWidget != null) {
            float target = targetWidgetAnim.getElapsedRatio();
            float connect = connectAnim.getElapsedRatio();
            float self = selfAnim.getElapsedRatio();
            if (!show){
                target = 1 - target;
                connect = 1 - connect;
                self = 1 - self;
            }
            EmphasizeRenderHelper.renderEmphasize(target, targetWidget, guiGraphics, getEmphasizeColor(), targetEmphasize);
            EmphasizeRenderHelper.connectV(connect,targetWidget.getLocationPoint(),getLocationPoint(getEmphasizeLocation()),guiGraphics,getEmphasizeColor());
            EmphasizeRenderHelper.renderEmphasize(self,this,getEmphasizeLocation(),guiGraphics,getEmphasizeColor(),selfEmphasize);
            guiGraphics.flush();
            RenderSystem.setShaderColor(1, 1, 1, self);
            IUIGuiUtils.drawString(guiGraphics, getMessage(), getX() + 1, getY() + 1, 0xFFFFFF, false);
            guiGraphics.flush();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }else {
            float connect = connectAnim.getElapsedRatio();
            float self = selfAnim.getElapsedRatio();
            if (!show){
                connect = 1 - connect;
                self = 1 - self;
            }
            EmphasizeRenderHelper.connectV(connect,targetPos,getLocationPoint(getEmphasizeLocation()),guiGraphics,getEmphasizeColor());
            EmphasizeRenderHelper.renderEmphasize(self,this,getEmphasizeLocation(),guiGraphics,getEmphasizeColor(),selfEmphasize);
            RenderSystem.setShaderColor(1, 1, 1, self);
            IUIGuiUtils.drawString(guiGraphics, getMessage(), getX() + 1, getY() + 1, 0xFFFFFF, false);
            guiGraphics.flush();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
        pose.popPose();
    }
}
