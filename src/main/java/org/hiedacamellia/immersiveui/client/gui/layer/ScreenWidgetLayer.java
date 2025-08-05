package org.hiedacamellia.immersiveui.client.gui.layer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGraphicUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;
import org.hiedacamellia.immersiveui.util.MouseCaptureUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.client.Minecraft.ON_OSX;

/**
 * ScreenWidgetLayer 是一个用于管理屏幕小部件的图层类。
 * 它实现了 LayeredDraw.Layer 接口，提供了屏幕渲染、鼠标交互和键盘事件处理的功能。
 */
@OnlyIn(Dist.CLIENT)
public class ScreenWidgetLayer implements LayeredDraw.Layer {

    /** 单例实例，用于全局访问 ScreenWidgetLayer。 */
    public static final ScreenWidgetLayer INSTANCE = new ScreenWidgetLayer();

    /** 屏幕图层的资源位置标识符。 */
    public static final ResourceLocation LOCATION = ImmersiveUI.rl("screen_layer");

    /** 当前显示的屏幕对象。 */
    @Nullable
    protected Screen screen;

    /**
     * 获取当前显示的屏幕。
     *
     * @return 当前的屏幕对象，如果没有屏幕则返回 null
     */
    public @Nullable Screen getScreen() {
        return screen;
    }

    /**
     * 设置当前显示的屏幕。
     * 初始化屏幕并捕获鼠标。
     *
     * @param screen 要设置的屏幕对象
     */
    public void setScreen(@NotNull Screen screen) {
        this.screen = screen;
        this.screen.init(Minecraft.getInstance(), IUIMinecraftUtil.getGuiScaledWidth(), IUIMinecraftUtil.getGuiScaledHeight());
        mouseX = IUIMinecraftUtil.getGuiScaledCenterX();
        mouseY = IUIMinecraftUtil.getGuiScaledCenterY();
        MouseCaptureUtil.startMouseCapture();
    }

    /**
     * 移除当前显示的屏幕。
     * 停止鼠标捕获。
     */
    public void removeScreen() {
        this.screen = null;
        MouseCaptureUtil.stopMouseCapture();
    }

    /**
     * 检查是否有屏幕正在显示。
     *
     * @return 如果有屏幕显示则返回 true，否则返回 false
     */
    public boolean hasScreen() {
        return screen != null;
    }

    /** 是否启用偏移效果。 */
    protected boolean enableOffset = true;

    /** 偏移因子，用于控制偏移效果的强度。 */
    protected float offsetFactor = 8.0f;

    /**
     * 设置是否启用偏移效果。
     *
     * @param enableOffset 是否启用偏移
     */
    public void setEnableOffset(boolean enableOffset) {
        this.enableOffset = enableOffset;
    }

    /**
     * 设置偏移因子。
     *
     * @param offsetFactor 偏移因子值
     */
    public void setOffsetFactor(float offsetFactor) {
        this.offsetFactor = offsetFactor;
    }

    /** 鼠标的 X 坐标。 */
    protected double mouseX;

    /** 鼠标的 Y 坐标。 */
    protected double mouseY;

    /**
     * 更新鼠标位置。
     * 根据提供的增量值调整鼠标位置，并限制在屏幕范围内。
     *
     * @param mouseX 鼠标 X 坐标的增量
     * @param mouseY 鼠标 Y 坐标的增量
     */
    public void addPos(double mouseX, double mouseY) {
        this.mouseX = Mth.clamp(this.mouseX + mouseX, 0, IUIMinecraftUtil.getGuiScaledWidth());
        this.mouseY = Mth.clamp(this.mouseY + mouseY, 0, IUIMinecraftUtil.getGuiScaledHeight());
    }

    /**
     * 处理鼠标点击事件。
     *
     * @param button 鼠标按键
     * @param action 按键动作（按下或释放）
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    public boolean click(int button, int action) {
        if (screen != null) {
            if (action == GLFW.GLFW_PRESS) {
                return screen.mouseClicked(mouseX, mouseY, button);
            } else if (action == GLFW.GLFW_RELEASE) {
                return screen.mouseReleased(mouseX, mouseY, button);
            }
        }
        return false;
    }

    /**
     * 处理键盘按键事件。
     *
     * @param keyCode   按键代码
     * @param scanCode  扫描代码
     * @param modifiers 修饰键
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (screen != null) {
            return screen.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }

    /**
     * 渲染屏幕小部件。
     *
     * @param guiGraphics 渲染上下文
     * @param deltaTracker 时间增量跟踪器
     */
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().player == null)
            return;
        if (Minecraft.getInstance().gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;
        if (Minecraft.getInstance().screen != null) {
            removeScreen();
        }

        if (screen != null) {
            int w = IUIMinecraftUtil.getGuiScaledWidth();
            int h = IUIMinecraftUtil.getGuiScaledHeight();

            double andResetCapturedDeltaX = MouseCaptureUtil.getAndResetCapturedDeltaX();
            double andResetCapturedDeltaY = MouseCaptureUtil.getAndResetCapturedDeltaY();
            addPos(andResetCapturedDeltaX, andResetCapturedDeltaY);

            double offsetX = enableOffset ? -andResetCapturedDeltaX / offsetFactor : 0;
            double offsetY = enableOffset ? -andResetCapturedDeltaY / offsetFactor : 0;

            PoseStack pose = guiGraphics.pose();
            pose.pushPose();

            ScreenTempTarget.SCREEN_INSTANCE.setClearColor(0, 0, 0, 0);
            ScreenTempTarget.SCREEN_INSTANCE.clear(ON_OSX);
            ScreenTempTarget.BLUR_INSTANCE.setClearColor(0, 0, 0, 0);
            ScreenTempTarget.BLUR_INSTANCE.clear(ON_OSX);

            RenderTarget mainRenderTarget = IUIMinecraftUtil.getMainRenderTarget();

            mainRenderTarget.unbindWrite();
            ScreenTempTarget.BLUR_INSTANCE.bindWrite(true);
            ScreenTempTarget.BLUR_INSTANCE.use = true;
            ScreenTempTarget.SCREEN_INSTANCE.use = true;

            // 将屏幕渲染到临时纹理
            IUIGraphicUtils.blit(pose, mainRenderTarget.getColorTextureId(), 0, 0, w, h, 0, 1, 1, 0);

            // 渲染屏幕
            screen.render(guiGraphics, (int) mouseX, (int) mouseY, deltaTracker.getGameTimeDeltaTicks());
            guiGraphics.flush();
            pose.popPose();

            ScreenTempTarget.SCREEN_INSTANCE.unbindWrite();
            ScreenTempTarget.BLUR_INSTANCE.use = false;
            ScreenTempTarget.SCREEN_INSTANCE.use = false;
            mainRenderTarget.bindWrite(true);

            // 将 blur 纹理渲染到屏幕
            pose.pushPose();
            RenderSystem.enableBlend();
            IUIGraphicUtils.blit(pose, ScreenTempTarget.BLUR_INSTANCE.getColorTextureId(), 0, 0, w, h, 0, 1, 1, 0);
            pose.popPose();

            // 渲染屏幕组件
            pose.pushPose();
            IUIGraphicUtils.blit(pose, ScreenTempTarget.SCREEN_INSTANCE.getColorTextureId(), (float) offsetX, (float) offsetY, (float) (offsetX + w), (float) (offsetY + h), 0, 1, 1, 0);
            pose.popPose();

            if (drawVirtualMouse)
                IUIGuiUtils.drawRing(guiGraphics, (float) mouseX, (float) mouseY, virtualMouseInnerRadius, virtualMouseOuterRadius, 0, 360, 0xFFFFFFFF, 0.1f);
        }
    }

    /** 是否绘制虚拟鼠标指针。 */
    protected boolean drawVirtualMouse = true;

    /**
     * 设置是否绘制虚拟鼠标指针。
     *
     * @param drawVirtualMouse 是否绘制虚拟鼠标
     */
    public void setDrawVirtualMouse(boolean drawVirtualMouse) {
        this.drawVirtualMouse = drawVirtualMouse;
    }

    /** 虚拟鼠标的内半径。 */
    protected float virtualMouseInnerRadius = 3.0f;

    /** 虚拟鼠标的外半径。 */
    protected float virtualMouseOuterRadius = 7.0f;

    /**
     * 设置虚拟鼠标的半径。
     *
     * @param innerRadius 内半径
     * @param outerRadius 外半径
     */
    public void setVirtualMouseRadius(float innerRadius, float outerRadius) {
        this.virtualMouseInnerRadius = innerRadius;
        this.virtualMouseOuterRadius = outerRadius;
    }
}