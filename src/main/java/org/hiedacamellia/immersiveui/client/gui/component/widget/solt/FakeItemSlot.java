package org.hiedacamellia.immersiveui.client.gui.component.widget.solt;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtil;
import org.hiedacamellia.immersiveui.util.holder.ItemStackHolder;
import org.lwjgl.glfw.GLFW;

public class FakeItemSlot extends FakeSlot {

    private ItemStackHolder holder = new ItemStackHolder();
    private boolean showDecoration = false;

    public ItemStack getItemStack() {
        return holder.get();
    }
    public void setItemStack(ItemStack itemStack) {
        holder.set(itemStack);
    }

    public void setShowDecoration(boolean showDecoration) {
        this.showDecoration = showDecoration;
    }

    public FakeItemSlot(int x, int y, Component message) {
        super(x, y, message);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        super.renderWidget(guiGraphics, mouseX, mouseY, v);
        guiGraphics.renderFakeItem(holder.get(), getX(), getY());
        if (showDecoration)
            guiGraphics.renderItemDecorations(IUIMinecraftUtil.getFont(), holder.get(), getX(), getY());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_RIGHT&&isHovered()){
            holder.set(ItemStack.EMPTY);
            return true;
        }
        return false;
    }
}
