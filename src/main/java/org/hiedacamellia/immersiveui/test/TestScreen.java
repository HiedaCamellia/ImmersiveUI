package org.hiedacamellia.immersiveui.test;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.gui.component.widget.component.ComponentWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeWidget;

public class TestScreen extends Screen {

    private TreeWidget treeWidget;



    protected TestScreen() {
        super(Component.empty());
    }


    @Override
    public void init() {
        super.init();
        TreeWidget subTreeWidget = new TreeWidget(0,0,Component.literal("TestTree1"),Minecraft.getInstance().font);
        TreeWidget subTreeWidget2 = new TreeWidget(0,0,Component.literal("TestTree3"),Minecraft.getInstance().font);
        subTreeWidget.addChild(new ComponentWidget(Component.literal("Test5")));
        subTreeWidget.addChild(new ComponentWidget(Component.literal("Test6")));
        subTreeWidget.addChild(new ComponentWidget(Component.literal("Test7")));
        subTreeWidget.addChild(subTreeWidget2);
        subTreeWidget.addChild(new ComponentWidget(Component.literal("Test8")));
        treeWidget = new TreeWidget(0,0,Component.literal("TestTree2"),Minecraft.getInstance().font);
        treeWidget.addChild(new ComponentWidget(Component.literal("Test1")));
        treeWidget.addChild(new ComponentWidget(Component.literal("Test2")));
        treeWidget.addChild(new ComponentWidget(Component.literal("Test3")));
        treeWidget.addChild(subTreeWidget);
        treeWidget.addChild(new ComponentWidget(Component.literal("Test4")));
        addRenderableWidget(treeWidget);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        guiGraphics.fill(0,0,this.width,this.height,0xFF000000);
    }
}
