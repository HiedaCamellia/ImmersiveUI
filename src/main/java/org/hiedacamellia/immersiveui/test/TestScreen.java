package org.hiedacamellia.immersiveui.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.hiedacamellia.immersiveui.ImmersiveUI;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base.BaseBarWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.bar.base.BaseTexBarWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.editbox.QuoteEditBox;
import org.hiedacamellia.immersiveui.client.gui.component.widget.price.ItemPriceWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.price.SimplePriceWidget;
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

        QuoteEditBox quoteEditBox = new QuoteEditBox(font,200,20,100,20,Component.empty());
        addRenderableWidget(quoteEditBox);

        Button reload = Button.builder(Component.literal("添加回复内容"),
                (button) -> {
//                    Minecraft.getInstance().reloadResourcePacks();
                    quoteEditBox.setQuoted(Component.literal("这是被回复的消息"));


                }).bounds(100, 0, 30, 10).build();
        addRenderableWidget(reload);

        BaseBarWidget bar = new BaseBarWidget(100, 50, 100, 10, Component.literal("Test"));
        bar.setBackColor(0xFF00FF00);
        bar.setProgress(0.2f);
        bar.setBorderWidth(2, 2);
//        addRenderableWidget(bar);

        BaseTexBarWidget baseTexBarWidget = new BaseTexBarWidget(100, 150, 100, 20, Component.empty());
        baseTexBarWidget.setTex(ImmersiveUI.rl("textures/test/test"));
        baseTexBarWidget.setProgress(0.5f);
        baseTexBarWidget.vertical();
//        addRenderableWidget(baseTexBarWidget);


        SimplePriceWidget simplePriceWidget = new SimplePriceWidget(200, 100,new ItemStack(Items.DIAMOND),10);
        addRenderableWidget(simplePriceWidget);

        ItemPriceWidget itemPriceWidget = new ItemPriceWidget(200, 130,new ItemStack(Items.DIAMOND),100,new ItemStack(Items.DIRT),1);
        addRenderableWidget(itemPriceWidget);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        //IUIGuiUtils.drawRing(guiGraphics,100,100,20,40,0,720,0xFFFFFFFF);
        IUIGuiUtils.blitRoundCentered(guiGraphics, ResourceLocation.withDefaultNamespace("textures/misc/unknown_pack.png"),100,100,50,0.2f);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        guiGraphics.fill(0,0,this.width,this.height,0xFF000000);
    }
}
