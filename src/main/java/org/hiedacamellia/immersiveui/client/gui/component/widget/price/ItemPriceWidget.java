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

/**
 * ItemPriceWidget 是一个自定义的 GUI 组件，用于显示和编辑物品价格。
 * 它包含两个物品槽、两个数值输入框以及一个等号组件，支持布局和渲染。
 */
public class ItemPriceWidget extends AbstractContainerWidget {

    private static final int width = 120; // 组件的宽度
    private static final int height = 20; // 组件的高度

    private final FakeItemSlot slot1; // 第一个物品槽
    private final FakeItemSlot slot2; // 第二个物品槽
    private final NumberEditBox editBox1; // 第一个数值输入框
    private final NumberEditBox editBox2; // 第二个数值输入框
    private final ComponentWidget componentWidget; // 等号组件

    private final LinearLayout layout; // 用于管理子组件的布局

    /**
     * 使用默认参数构造一个 ItemPriceWidget 实例。
     *
     * @param x 组件的 X 坐标
     * @param y 组件的 Y 坐标
     */
    public ItemPriceWidget(int x, int y) {
        this(x, y, ItemStack.EMPTY, 0, ItemStack.EMPTY, 0);
    }

    /**
     * 尝试将物品放入物品槽中。
     *
     * @param itemStack 要放入的物品堆
     * @return 如果成功放入物品槽，则返回 true；否则返回 false
     */
    public boolean tryAccept(ItemStack itemStack) {
        if (slot1.isHovered()) {
            slot1.setItemStack(itemStack);
            slot1.setMessage(itemStack.getDisplayName());
            editBox1.setInt(itemStack.getCount());
            return true;
        }
        if (slot2.isHovered()) {
            slot2.setItemStack(itemStack);
            slot2.setMessage(itemStack.getDisplayName());
            editBox2.setInt(itemStack.getCount());
            return true;
        }
        return false;
    }

    /**
     * 使用指定参数构造一个 ItemPriceWidget 实例。
     *
     * @param x          组件的 X 坐标
     * @param y          组件的 Y 坐标
     * @param want       第一个物品槽的物品
     * @param want_count 第一个物品槽的物品数量
     * @param sell       第二个物品槽的物品
     * @param sell_count 第二个物品槽的物品数量
     */
    public ItemPriceWidget(int x, int y, ItemStack want, int want_count, ItemStack sell, int sell_count) {
        super(x, y, width, height, Component.empty());

        slot1 = new FakeItemSlot(0, 0, want.getDisplayName());
        slot1.setItemStack(want);
        slot2 = new FakeItemSlot(0, 0, sell.getDisplayName());
        slot2.setItemStack(sell);
        componentWidget = new ComponentWidget(0, 0, Component.literal("="));
        editBox1 = new NumberEditBox(0, 0, 30, 9, Component.empty());
        editBox1.setInt(want_count);
        editBox1.setBordered(false);
        editBox2 = new NumberEditBox(0, 0, 30, 9, Component.empty());
        editBox2.setInt(sell_count);
        editBox2.setBordered(false);

        layout = new LinearLayout(width, height, LinearLayout.Orientation.HORIZONTAL);
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

    /**
     * 获取第一个物品槽中的物品堆。
     *
     * @return 第一个物品槽中的物品堆
     */
    public ItemStack getItemStackWant() {
        return slot1.getItemStack();
    }

    /**
     * 获取第二个物品槽中的物品堆。
     *
     * @return 第二个物品槽中的物品堆
     */
    public ItemStack getItemStackSell() {
        return slot2.getItemStack();
    }

    /**
     * 获取第一个物品槽中的物品数量。
     *
     * @return 第一个物品槽中的物品数量
     */
    public int getCountWant() {
        return editBox1.getInt();
    }

    /**
     * 获取第二个物品槽中的物品数量。
     *
     * @return 第二个物品槽中的物品数量
     */
    public int getCountSell() {
        return editBox2.getInt();
    }

    /**
     * 渲染组件及其子组件。
     *
     * @param guiGraphics 渲染上下文
     * @param mouseX      鼠标的 X 坐标
     * @param mouseY      鼠标的 Y 坐标
     * @param v           渲染的部分时间
     */
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

    /**
     * 更新组件的旁白信息。
     *
     * @param narrationElementOutput 旁白输出对象
     */
    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        // 未实现旁白更新逻辑
    }

    /**
     * 获取组件的子组件列表。
     *
     * @return 子组件列表
     */
    @Override
    public List<? extends GuiEventListener> children() {
        return List.of(slot1, editBox1, slot2, editBox2);
    }

    @Override
    protected int contentHeight() {
        return getHeight();
    }

    @Override
    protected double scrollRate() {
        return 0;
    }
}