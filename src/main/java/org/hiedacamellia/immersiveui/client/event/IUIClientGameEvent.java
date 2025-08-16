package org.hiedacamellia.immersiveui.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.api.event.ResizeDisplayEvent;
import org.hiedacamellia.immersiveui.api.event.SetScreenEvent;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.action.BuiltInWheelActions;
import org.hiedacamellia.immersiveui.client.gui.layer.ScreenWidgetLayer;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.lwjgl.glfw.GLFW;

/**
 * IUIClientGameEvent 是一个客户端事件处理类。
 * 它监听并处理与客户端相关的各种事件，例如键盘输入、鼠标操作和屏幕事件。
 */
@EventBusSubscriber(modid = ImmersiveUI.MODID, value = Dist.CLIENT)
public class IUIClientGameEvent {

    /**
     * 客户端设置事件的处理方法。
     * 在客户端初始化时调用，用于设置屏幕目标和初始化内置操作。
     *
     * @param event 客户端设置事件
     */
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ScreenTempTarget.SCREEN_INSTANCE = new ScreenTempTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
            ScreenTempTarget.BLUR_INSTANCE = new ScreenTempTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());

            BuiltInWheelActions.init();
        });
    }

    /**
     * 键盘输入事件的处理方法。
     *
     * @param event 键盘输入事件
     */
    @SubscribeEvent
    public static void onKey(final InputEvent.Key event) {
        // 键盘输入事件的处理逻辑
    }

    /**
     * 鼠标按键事件的处理方法。
     * 用于处理鼠标点击事件，并根据点击位置触发相应的操作。
     *
     * @param event 鼠标按键事件
     */
    @SubscribeEvent
    public static void onMouse(final InputEvent.MouseButton.Pre event) {
        if (Minecraft.getInstance().level == null) return;

        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (event.getAction() == InputConstants.PRESS) {
                boolean consumed = World2ScreenWidgetLayer.INSTANCE.click(event.getButton());
                event.setCanceled(consumed);
            }
        }

        event.setCanceled(ScreenWidgetLayer.INSTANCE.click(event.getButton(), event.getAction()));
    }

    /**
     * 鼠标滚轮事件的处理方法。
     * 用于处理鼠标滚动操作，并触发相应的滚动事件。
     *
     * @param event 鼠标滚轮事件
     */
    @SubscribeEvent
    public static void onMouseScroll(final InputEvent.MouseScrollingEvent event) {
        if (Minecraft.getInstance().level == null) return;

        event.setCanceled(World2ScreenWidgetLayer.INSTANCE.scroll(event.getMouseX(), event.getMouseY(), event.getScrollDeltaX(), event.getScrollDeltaY()));
    }

    /**
     * 屏幕打开事件的处理方法。
     * 用于在特定条件下替换或取消屏幕的打开操作。
     *
     * @param event 屏幕打开事件
     */
    @SubscribeEvent
    public static void onScreen(ScreenEvent.Opening event) {
        if (World2ScreenWidgetLayer.INSTANCE.activeScreen != null) {
            World2ScreenWidgetLayer.INSTANCE.activeScreen.setScreen(event.getNewScreen());
            event.setCanceled(true);
        }
        // 以下代码被注释掉，可能用于暂停屏幕的特殊处理逻辑
        // else {
        //     if (event.getNewScreen() instanceof PauseScreen pauseScreen) {
        //         UUID uuid = UUID.randomUUID();
        //         World2ScreenScreen screenScreen = new World2ScreenScreen(uuid, pauseScreen, Minecraft.getInstance().player);
        //         World2ScreenWidgetLayer.INSTANCE.addWorldPositionObject(uuid, screenScreen);
        //         World2ScreenWidgetLayer.INSTANCE.setActiveScreen(screenScreen);
        //         event.setCanceled(true);
        //     }
        // }
    }

    @SubscribeEvent
    public static void onResize(ResizeDisplayEvent event){
        Window window = Minecraft.getInstance().getWindow();
        if(ScreenTempTarget.BLUR_INSTANCE ==null)return;
        ScreenTempTarget.BLUR_INSTANCE.resize(window.getWidth(), window.getHeight(),true);
        if(ScreenTempTarget.SCREEN_INSTANCE ==null)return;
        ScreenTempTarget.SCREEN_INSTANCE.resize(window.getWidth(), window.getHeight(),true);
        ScreenTempTarget.getBlurEffect().resize(window.getWidth(), window.getHeight());
        World2ScreenWidgetLayer.INSTANCE.resize();
    }

    @SubscribeEvent
    public static void onSetScreen(SetScreenEvent event){
        if(event.getScreen()==null&&World2ScreenWidgetLayer.INSTANCE.activeScreen !=null){
            World2ScreenWidgetLayer.INSTANCE.remove(World2ScreenWidgetLayer.INSTANCE.activeScreen.getId());
        }
    }
}