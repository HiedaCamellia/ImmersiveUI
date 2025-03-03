package org.hiedacamellia.immersiveui.test;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.immersiveui.client.graphic.gui.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.component.ComponentWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeEntryWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeWidget;

public class TestScreen extends Screen {

    private TreeWidget<Component,TreeEntryWidget<Component>> treeWidget;



    protected TestScreen() {
        super(Component.empty());
    }


    @Override
    public void init() {
        super.init();
        Font font1 = Minecraft.getInstance().font;
        TreeEntryWidget<Component> subTreeWidget = new TreeEntryWidget<>(Component.literal("TestTree1"), font1);
        TreeEntryWidget<Component> subTreeWidget2 = new TreeEntryWidget<>(Component.literal("TestTree2"), font1);
        TreeEntryWidget<Component> subTreeWidget3 = new TreeEntryWidget<>(Component.literal("TestTree3"), font1);
        subTreeWidget.addChild(TreeEntryWidget.of(Component.literal("Test5"),Component.literal("Test5"), font1));
        subTreeWidget.addChild(TreeEntryWidget.of(Component.literal("Test6"),Component.literal("Test6"), font1));
        subTreeWidget.addChild(TreeEntryWidget.of(Component.literal("Test7"),Component.literal("Test7"), font1));
        subTreeWidget.addChild(subTreeWidget2);
        subTreeWidget.addChild(TreeEntryWidget.of(Component.literal("Test8"),Component.literal("Test8"), font1));

        subTreeWidget3.addChild(TreeEntryWidget.of(Component.literal("Test1"),Component.literal("Test1"), font1));
        subTreeWidget3.addChild(TreeEntryWidget.of(Component.literal("Test2"),Component.literal("Test2"), font1));
        subTreeWidget3.addChild(TreeEntryWidget.of(Component.literal("Test3"),Component.literal("Test3"), font1));
        subTreeWidget3.addChild(subTreeWidget);
        subTreeWidget3.addChild(TreeEntryWidget.of(Component.literal("Test4"),Component.literal("Test4"), font1));

        treeWidget = TreeWidget.of(subTreeWidget3,0,0,Component.literal("Test"),font1);

        addRenderableWidget(treeWidget);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        //IUIGuiUtils.drawRing(guiGraphics,100,100,20,40,0,720,0xFFFFFFFF);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        guiGraphics.fill(0,0,this.width,this.height,0xFF000000);
    }
}
