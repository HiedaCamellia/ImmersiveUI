package org.hiedacamellia.immersiveui.client.gui.component.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.target.ScreenTempTarget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.WheelTreeEntryWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.wheel.WheelTreeWidget;

import java.util.List;

/**
 * WheelMenuScreen 是一个抽象类，表示带有轮盘菜单的屏幕。
 * 提供了轮盘树组件的创建、初始化和渲染功能。
 */
public abstract class WheelMenuScreen extends Screen {

    /** 轮盘树组件，用于显示轮盘菜单。 */
    private WheelTreeWidget wheelTreeWidget;

    /**
     * 获取当前的轮盘树组件。
     *
     * @return 当前的 WheelTreeWidget 实例
     */
    public WheelTreeWidget getTree(){
        return wheelTreeWidget;
    }

    /** 是否渲染背景。 */
    public boolean renderBackground = true;

    /**
     * 构造函数，初始化屏幕标题。
     *
     * @param title 屏幕的标题
     */
    public WheelMenuScreen(Component title) {
        super(title);
    }

    /**
     * 初始化屏幕，创建并添加轮盘树组件。
     */
    @Override
    public void init(){
        super.init();
        wheelTreeWidget = createWheelTreeWidget();
        addRenderableWidget(wheelTreeWidget);
    }

    /**
     * 创建轮盘树组件。
     *
     * @return 创建的 WheelTreeWidget 实例
     */
    public WheelTreeWidget createWheelTreeWidget(){
        return WheelTreeWidget.create(getWheelEntries(), 0, 0, getTitle(), font);
    }

    /**
     * 获取轮盘条目列表。
     * 需要由子类实现以提供具体的条目。
     *
     * @return 轮盘条目列表
     */
    public abstract List<? extends WheelTreeEntryWidget> getWheelEntries();

    /**
     * 渲染屏幕背景。
     * 如果 renderBackground 为 true，则调用父类的背景渲染方法。
     * 否则，切换到屏幕临时目标进行渲染。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分刻度
     */
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(renderBackground) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        } else {
            ScreenTempTarget.BLUR_INSTANCE.unbindWrite();
            ScreenTempTarget.SCREEN_INSTANCE.bindWrite(false);
        }
    }
}