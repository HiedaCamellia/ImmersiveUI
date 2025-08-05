package org.hiedacamellia.immersiveui.client.gui.component.widget.solt;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIMinecraftUtils;
import org.hiedacamellia.immersiveui.util.holder.ItemStackHolder;
import org.lwjgl.glfw.GLFW;

/**
 * FakeItemSlot 是一个扩展自 FakeSlot 的自定义 GUI 组件，
 * 用于表示一个虚拟的物品槽，支持显示物品堆和装饰效果。
 */
public class FakeItemSlot extends FakeSlot {

    private ItemStackHolder holder = new ItemStackHolder(); // 持有物品堆的对象
    private boolean showDecoration = false; // 是否显示装饰效果

    /**
     * 获取物品槽中的物品堆。
     *
     * @return 当前物品槽中的物品堆
     */
    public ItemStack getItemStack() {
        return holder.get();
    }

    /**
     * 设置物品槽中的物品堆。
     *
     * @param itemStack 要设置的物品堆
     */
    public void setItemStack(ItemStack itemStack) {
        holder.set(itemStack);
    }

    /**
     * 设置是否显示装饰效果。
     *
     * @param showDecoration 如果为 true，则显示装饰效果；否则不显示
     */
    public void setShowDecoration(boolean showDecoration) {
        this.showDecoration = showDecoration;
    }

    /**
     * 构造一个 FakeItemSlot 实例。
     *
     * @param x       物品槽的 X 坐标
     * @param y       物品槽的 Y 坐标
     * @param message 提示信息
     */
    public FakeItemSlot(int x, int y, Component message) {
        super(x, y, message);
    }

    /**
     * 渲染物品槽组件，包括物品堆和装饰效果。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        super.renderWidget(guiGraphics, mouseX, mouseY, v);
        guiGraphics.renderFakeItem(holder.get(), getX(), getY());
        if (showDecoration)
            guiGraphics.renderItemDecorations(IUIMinecraftUtils.getFont(), holder.get(), getX(), getY());
    }

    /**
     * 更新组件的旁白信息。
     * 当前实现未定义任何旁白逻辑。
     *
     * @param narrationElementOutput 旁白输出对象
     */
    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    /**
     * 处理鼠标点击事件。
     * 如果右键点击物品槽，则清空物品堆。
     *
     * @param mouseX 鼠标的 X 坐标
     * @param mouseY 鼠标的 Y 坐标
     * @param button 鼠标按键
     * @return 如果事件被处理，则返回 true；否则返回 false
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && isHovered()) {
            holder.set(ItemStack.EMPTY);
            return true;
        }
        return false;
    }
}