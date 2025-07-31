package org.hiedacamellia.immersiveui.client.gui.component.w2s;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGraphicUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.joml.Vector3f;

import java.util.UUID;

import static net.minecraft.client.Minecraft.ON_OSX;

/**
 * World2ScreenScreen 是一个从世界坐标到屏幕坐标的可渲染组件。
 * 它继承自 World2ScreenWidget，并提供了屏幕渲染、交互和缩放计算的功能。
 */
public class World2ScreenScreen extends World2ScreenWidget {

    /** Minecraft 实例，用于访问游戏相关的上下文。 */
    protected Minecraft minecraft = Minecraft.getInstance();

    /** 当前显示的屏幕对象。 */
    protected Screen screen;

    /** 当前玩家对象。 */
    protected Player player;

    /** 屏幕的宽度。 */
    protected int w;

    /** 屏幕的高度。 */
    protected int h;

    /** 组件的世界坐标位置。 */
    protected Vec3 pos;

    /** 主渲染目标，用于屏幕渲染。 */
    protected final RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();

    /**
     * 设置当前显示的屏幕。
     * 初始化屏幕并设置其宽度和高度。
     *
     * @param screen 要设置的屏幕对象
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
        screen.init(minecraft, w, h);
    }

    /**
     * 获取当前显示的屏幕。
     *
     * @return 当前的屏幕对象
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * 调整组件的大小。
     * 更新屏幕的宽度和高度。
     */
    @Override
    public void resize() {
        this.w = minecraft.getWindow().getGuiScaledWidth();
        this.h = minecraft.getWindow().getGuiScaledHeight();
    }

    /**
     * 检查是否为相同的屏幕。
     *
     * @param screen 要比较的屏幕对象
     * @return 如果是相同的屏幕则返回 true，否则返回 false
     */
    public boolean isSameScreen(Screen screen) {
        return this.screen == screen;
    }

    /**
     * 处理键盘按键事件。
     *
     * @param keyCode 按键代码
     * @param scanCode 扫描代码
     * @param modifiers 修饰键
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.screen.keyPressed(keyCode, scanCode, modifiers);
    }

    /**
     * 构造函数，使用 UUID、屏幕和玩家对象初始化组件。
     *
     * @param uuid 组件的唯一标识符
     * @param screen 当前显示的屏幕
     * @param player 当前玩家对象
     */
    public World2ScreenScreen(UUID uuid, Screen screen, Player player) {
        this(uuid, screen, player, getPlayerEye(player));
    }

    /**
     * 获取玩家的眼睛位置。
     *
     * @param player 玩家对象
     * @return 玩家眼睛的世界坐标
     */
    private static Vec3 getPlayerEye(Player player) {
        Vec3 eyePosition = player.getEyePosition();
        Vec3 lookAngle = player.getLookAngle();
        Vec3 normalizedLookAngle = lookAngle.normalize();
        return eyePosition.add(normalizedLookAngle);
    }

    /**
     * 构造函数，使用 UUID、屏幕、玩家对象和位置初始化组件。
     *
     * @param uuid 组件的唯一标识符
     * @param screen 当前显示的屏幕
     * @param player 当前玩家对象
     * @param pos 组件的世界坐标位置
     */
    public World2ScreenScreen(UUID uuid, Screen screen, Player player, Vec3 pos) {
        super(uuid);
        this.w = minecraft.getWindow().getGuiScaledWidth();
        this.h = minecraft.getWindow().getGuiScaledHeight();
        this.screen = screen;
        this.screen.init(minecraft, w, h);
        this.player = player;
        this.pos = pos;
        this.scale = 1;
    }

    /**
     * 处理鼠标点击事件。
     *
     * @param button 鼠标按键
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    @Override
    public boolean click(int button) {
        int mX = (int) (((float) w - x) / scale);
        int mY = (int) (((float) h - y) / scale);
        World2ScreenWidgetLayer.INSTANCE.setActiveScreen(this);
        return screen.mouseClicked(mX, mY, button);
    }

    /**
     * 处理鼠标滚动事件。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param scrollX 滚动的 X 方向增量
     * @param scrollY 滚动的 Y 方向增量
     * @return 如果事件被处理则返回 true，否则返回 false
     */
    @Override
    public boolean scroll(double mouseX, double mouseY, double scrollX, double scrollY) {
        double mX = (((float) w - x) / scale);
        double mY = (((float) h - y) / scale);
        return screen.mouseScrolled(mX, mY, scrollX, scrollY);
    }

    /**
     * 获取组件的世界坐标。
     *
     * @param out 用于存储世界坐标的 Vector3f 对象
     */
    @Override
    public void getWorldPos(Vector3f out) {
        out.set(pos.toVector3f());
    }

    /**
     * 渲染组件。
     *
     * @param guiGraphics 渲染上下文
     * @param highlight 是否高亮显示
     * @param value 渲染附加值
     * @param deltaTracker 时间增量跟踪器
     */
    @Override
    public void render(GuiGraphics guiGraphics, boolean highlight, float value, DeltaTracker deltaTracker) {

        ScreenTempTarget.SCREEN_INSTANCE.setClearColor(0, 0, 0, 0);
        ScreenTempTarget.SCREEN_INSTANCE.clear();
        ScreenTempTarget.BLUR_INSTANCE.setClearColor(0, 0, 0, 0);
        ScreenTempTarget.BLUR_INSTANCE.clear();

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();

        float x1 = x - (float) w / 2;
        float y1 = y - (float) h / 2;
        float x2 = scale * w + x1;
        float y2 = scale * h + y1;
        int mX = (int) (((float) w - x) / scale);
        int mY = (int) (((float) h - y) / scale);

        mainRenderTarget.unbindWrite();
        ScreenTempTarget.BLUR_INSTANCE.bindWrite(true);
        ScreenTempTarget.BLUR_INSTANCE.use = true;
        ScreenTempTarget.SCREEN_INSTANCE.use = true;

        // 将屏幕渲染到临时纹理
        IUIGraphicUtils.blit(pose, mainRenderTarget.getColorTextureId(), 0, 0, w, h, 0, 1, 1, 0);

        // 渲染屏幕
        screen.render(guiGraphics, mX, mY, deltaTracker.getGameTimeDeltaTicks());
        guiGraphics.flush();
        pose.popPose();

        ScreenTempTarget.SCREEN_INSTANCE.unbindWrite();
        ScreenTempTarget.BLUR_INSTANCE.use = false;
        ScreenTempTarget.SCREEN_INSTANCE.use = false;
        mainRenderTarget.bindWrite(true);

        // 将 blur 纹理渲染到屏幕
        pose.pushPose();
        float u0 = x1 / w;
        float v0 = y2 / h;
        float u1 = x2 / w;
        float v1 = y1 / h;
        RenderSystem.enableBlend();
        IUIGraphicUtils.blitInUv(pose, ScreenTempTarget.BLUR_INSTANCE.getColorTextureId(), 0, 0, w, h, u0, 1 - v1, u1, 1 - v0);
        pose.popPose();

        // 渲染屏幕组件
        pose.pushPose();
        pose.translate(x - (float) w / 2, y - (float) h / 2, 100);
        pose.scale(scale, scale, 1);
        IUIGraphicUtils.blit(pose, ScreenTempTarget.SCREEN_INSTANCE.getColorTextureId(), 0, 0, w, h, 0, 1, 1, 0);
        pose.popPose();

        // 渲染指针
        if (mX >= 0 && mY >= 0 && mX <= w && mY <= h) {
            pose.pushPose();
            pose.translate(0, 0, 200);
            Minecraft.getInstance().gui.renderCrosshair(guiGraphics, deltaTracker);
            pose.popPose();
            pose.pushPose();
        }
    }

    /**
     * 根据距离平方值计算渲染缩放比例。
     * 如果距离过远，则移除组件。
     *
     * @param distanceSqr 距离的平方值
     */
    @Override
    public void calculateRenderScale(float distanceSqr) {
        this.scale = (float) (2 * Math.atan2(1.0, Math.sqrt(distanceSqr)));
        if (this.scale > 1) {
            this.scale = 1;
        }

        if (distanceSqr > 64 && !shouldRemove) {
            World2ScreenWidgetLayer.INSTANCE.remove(uuid);
        }
    }
}