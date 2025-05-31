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

public class SimplePriceWidget extends AbstractContainerWidget{

    private static final int width = 80;
    private static final int height = 20;

    private FakeItemSlot slot;
    private NumberEditBox editBox;
    private ComponentWidget componentWidget;

    private LinearLayout layout;

    public SimplePriceWidget(int x, int y) {
        this(x,y,ItemStack.EMPTY,0);
    }

    public boolean tryAccept(ItemStack itemStack){
        if(slot.isHovered()){
            slot.setItemStack(itemStack);
            slot.setMessage(itemStack.getDisplayName());
            return true;
        }
        return false;
    }

    public SimplePriceWidget(int x, int y, ItemStack itemStack, int price) {
        super(x, y, width, height, Component.empty());

        slot = new FakeItemSlot(0,0, itemStack.getDisplayName());
        slot.setItemStack(itemStack);
        componentWidget = new ComponentWidget(0, 0, Component.literal("="));
        editBox = new NumberEditBox(0,0, 30, 9, Component.empty());
        editBox.setInt(price);
        editBox.setBordered(false);
        //editBox.setFGColor(0x00000000);
        layout = new LinearLayout(width,height, LinearLayout.Orientation.HORIZONTAL);
        layout.setX(x);
        layout.setY(y);
        layout.spacing(2);
        layout.defaultCellSetting().alignHorizontallyCenter().alignVerticallyMiddle()
                .paddingVertical(2).paddingHorizontal(2);


        layout.addChild(slot);
        layout.addChild(componentWidget);
        layout.addChild(editBox);
        layout.arrangeElements();

    }

    public ItemStack getItemStack() {
        return slot.getItemStack();
    }

    public int getPrice() {
        return editBox.getInt();
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        RenderSystem.enableBlend();
        IUIGuiUtils.fillRoundRect(guiGraphics, getX(), getY(), width, height, 0.05f, 0xFFFEEBD1);
        RenderSystem.disableBlend();
        slot.render(guiGraphics, mouseX, mouseY, v);
        componentWidget.render(guiGraphics, mouseX, mouseY, v);
        editBox.render(guiGraphics, mouseX, mouseY, v);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of(slot,editBox);
    }
}
