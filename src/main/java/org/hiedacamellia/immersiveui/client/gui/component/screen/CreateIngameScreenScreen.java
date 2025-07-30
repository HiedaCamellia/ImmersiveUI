package org.hiedacamellia.immersiveui.client.gui.component.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;

import java.util.UUID;

/**
 * CreateIngameScreenScreen 是一个用于在游戏内创建屏幕的类。
 * 它继承自 Screen 类，并负责将屏幕转换为世界坐标组件。
 */
public class CreateIngameScreenScreen extends Screen {

    /** 要显示的屏幕对象。 */
    private Screen screen;

    /**
     * 构造函数，初始化 CreateIngameScreenScreen 实例。
     *
     * @param screen 要显示的屏幕对象
     */
    public CreateIngameScreenScreen(Screen screen) {
        super(Component.empty());
        this.screen = screen;
    }

    /**
     * 初始化方法。
     * 如果当前没有加载世界，则关闭屏幕。
     * 否则，将屏幕转换为 World2ScreenScreen 并添加到 World2ScreenWidgetLayer 中。
     */
    public void init() {
        if (Minecraft.getInstance().level == null) {
            onClose();
        } else {
            onClose();
            UUID uuid = UUID.randomUUID();
            World2ScreenScreen world2ScreenScreen = new World2ScreenScreen(uuid, screen, Minecraft.getInstance().player);
            World2ScreenWidgetLayer.INSTANCE.addWorldPositionObject(uuid, world2ScreenScreen);
            World2ScreenWidgetLayer.INSTANCE.setActiveScreen(world2ScreenScreen);
        }
    }
}