package org.hiedacamellia.immersiveui.client.gui.component.fakescreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.gui.layer.ScreenWidgetLayer;

/**
 * CreateScreenLayerScreenScreen 是一个用于在游戏内创建屏幕图层的类。
 * 它继承自 Screen 类，并负责将指定的屏幕设置为 ScreenWidgetLayer 的当前屏幕。
 */
public class CreateScreenLayerScreenScreen extends Screen {

    /** 要显示的屏幕对象。 */
    private Screen screen;

    /**
     * 构造函数，初始化 CreateScreenLayerScreenScreen 实例。
     *
     * @param screen 要显示的屏幕对象
     */
    public CreateScreenLayerScreenScreen(Screen screen) {
        super(Component.empty());
        this.screen = screen;
    }

    /**
     * 初始化方法。
     * 如果当前没有加载世界，则关闭屏幕。
     * 否则，将指定的屏幕设置为 ScreenWidgetLayer 的当前屏幕。
     */
    public void init() {
        if (Minecraft.getInstance().level == null) {
            onClose();
        } else {
            onClose();
            ScreenWidgetLayer.INSTANCE.setScreen(screen);
        }
    }
}