package org.hiedacamellia.immersiveui.client.debug;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.NeoForge;
import org.hiedacamellia.immersiveui.api.event.CollectDebugTreeRootEvent;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeEntryWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.debug.DebugTreeWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * DebugScreen 是一个调试屏幕类，继承自 Minecraft 的 Screen 类。
 * 它用于显示调试信息，并通过 DebugTreeWidget 组织和呈现调试树。
 */
public class DebugScreen extends Screen {

    /** 调试树小部件，用于显示调试信息的树状结构。 */
    private static DebugTreeWidget debugTreeWidget;

    /**
     * 构造函数，初始化 DebugScreen 实例。
     * 设置屏幕的标题为 "Debug Screen"。
     */
    protected DebugScreen() {
        super(Component.literal("Debug Screen"));
    }

    /**
     * 初始化方法，在屏幕被打开时调用。
     * 它会收集调试树的根节点并创建调试树小部件。
     */
    @Override
    protected void init() {
        // 创建一个存储调试树根节点的列表
        List<DebugTreeEntryWidget> roots = new ArrayList<>();
        // 发布 CollectDebugTreeRootEvent 事件以收集调试树根节点
        NeoForge.EVENT_BUS.post(new CollectDebugTreeRootEvent(roots));
        // 创建调试树小部件并添加到屏幕中
        debugTreeWidget = DebugTreeWidget.create(roots, 0, 0, Component.literal("Debug"), font);
        addRenderableWidget(debugTreeWidget);
    }

    /**
     * 渲染方法，用于绘制屏幕内容。
     *
     * @param guiGraphics 渲染图形对象
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param partialTick 渲染的部分刻度时间
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 调用父类的渲染方法
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}