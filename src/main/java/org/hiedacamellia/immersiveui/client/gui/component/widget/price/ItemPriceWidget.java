package org.hiedacamellia.immersiveui.client.gui.component.widget.price;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.component.ComponentWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.editbox.NumberEditBox;
import org.hiedacamellia.immersiveui.client.gui.component.widget.solt.FakeItemSlot;

import java.util.List;

public class ItemPriceWidget extends AbstractContainerWidget{

    private static final int width = 120;
    private static final int height = 20;

    private FakeItemSlot slot1;
    private FakeItemSlot slot2;
    private NumberEditBox editBox1;
    private NumberEditBox editBox2;
    private ComponentWidget componentWidget;

    private LinearLayout layout;

    public ItemPriceWidget(int x, int y) {
        this(x,y,ItemStack.EMPTY,0,ItemStack.EMPTY,0);
    }

    public boolean tryAccept(ItemStack itemStack){
        if(slot1.isHovered()){
            slot1.setItemStack(itemStack);
            slot1.setMessage(itemStack.getDisplayName());
            editBox1.setInt(itemStack.getCount());
            return true;
        }
        if(slot2.isHovered()){
            slot2.setItemStack(itemStack);
            slot2.setMessage(itemStack.getDisplayName());
            editBox2.setInt(itemStack.getCount());
            return true;
        }
        return false;
    }

    public ItemPriceWidget(int x, int y, ItemStack want,int want_count,ItemStack sell, int sell_count) {
        super(x, y, width, height, Component.empty());

        slot1 = new FakeItemSlot(0,0, want.getDisplayName());
        slot1.setItemStack(want);
        slot2 = new FakeItemSlot(0,0, sell.getDisplayName());
        slot2.setItemStack(sell);
        componentWidget = new ComponentWidget(0, 0, Component.literal("="));
        editBox1 = new NumberEditBox(0,0, 30, 9, Component.empty());
        editBox1.setInt(want_count);
        editBox1.setBordered(false);
        editBox2 = new NumberEditBox(0,0, 30, 9, Component.empty());
        editBox2.setInt(sell_count);
        editBox2.setBordered(false);
        //editBox1.setFGColor(0x00000000);
        layout = new LinearLayout(width,height, LinearLayout.Orientation.HORIZONTAL);
        layout.setX(x);
        layout.setY(y);
        layout.spacing(2);
        layout.defaultCellSetting().alignHorizontallyCenter().alignVerticallyMiddle()
                .paddingVertical(2).paddingHorizontal(2);


        layout.addChild(slot1);
        layout.addChild(editBox1);
        layout.addChild(componentWidget);
        layout.addChild(slot2);
        layout.addChild(editBox2);
        layout.arrangeElements();

    }

    public ItemStack getItemStackWant() {
        return slot1.getItemStack();
    }
    public ItemStack getItemStackSell() {
        return slot2.getItemStack();
    }

    public int getCountWant() {
        return editBox1.getInt();
    }
    public int getCountSell() {
        return editBox2.getInt();
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        RenderSystem.enableBlend();
        IUIGuiUtils.fillRoundRect(guiGraphics, getX(), getY(), width, height, 0.05f, 0xFFFEEBD1);
        RenderSystem.disableBlend();
        slot1.render(guiGraphics, mouseX, mouseY, v);
        slot2.render(guiGraphics, mouseX, mouseY, v);
        componentWidget.render(guiGraphics, mouseX, mouseY, v);
        editBox1.render(guiGraphics, mouseX, mouseY, v);
        editBox2.render(guiGraphics, mouseX, mouseY, v);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of(slot1, editBox1,slot2,editBox2);
    }
}
