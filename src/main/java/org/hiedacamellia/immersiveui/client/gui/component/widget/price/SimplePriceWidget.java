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
 * SimplePriceWidget 是一个自定义的 GUI 组件，用于显示和编辑单个物品的价格。
 * 它包含一个物品槽、一个数值输入框以及一个等号组件，支持布局和渲染。
 */
public class SimplePriceWidget extends AbstractContainerWidget {

    private static final int width = 80; // 组件的宽度
    private static final int height = 20; // 组件的高度

    private final FakeItemSlot slot; // 物品槽
    private final NumberEditBox editBox; // 数值输入框
    private final ComponentWidget componentWidget; // 等号组件

    private final LinearLayout layout; // 用于管理子组件的布局

    /**
     * 使用默认参数构造一个 SimplePriceWidget 实例。
     *
     * @param x 组件的 X 坐标
     * @param y 组件的 Y 坐标
     */
    public SimplePriceWidget(int x, int y) {
        this(x, y, ItemStack.EMPTY, 0);
    }

    /**
     * 尝试将物品放入物品槽中。
     *
     * @param itemStack 要放入的物品堆
     * @return 如果成功放入物品槽，则返回 true；否则返回 false
     */
    public boolean tryAccept(ItemStack itemStack) {
        if (slot.isHovered()) {
            slot.setItemStack(itemStack);
            slot.setMessage(itemStack.getDisplayName());
            return true;
        }
        return false;
    }

    /**
     * 使用指定参数构造一个 SimplePriceWidget 实例。
     *
     * @param x         组件的 X 坐标
     * @param y         组件的 Y 坐标
     * @param itemStack 物品槽中的物品
     * @param price     物品的价格
     */
    public SimplePriceWidget(int x, int y, ItemStack itemStack, int price) {
        super(x, y, width, height, Component.empty());

        slot = new FakeItemSlot(0, 0, itemStack.getDisplayName());
        slot.setItemStack(itemStack);
        componentWidget = new ComponentWidget(0, 0, Component.literal("="));
        editBox = new NumberEditBox(0, 0, 30, 9, Component.empty());
        editBox.setInt(price);
        editBox.setBordered(false);

        layout = new LinearLayout(width, height, LinearLayout.Orientation.HORIZONTAL);
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

    /**
     * 获取物品槽中的物品堆。
     *
     * @return 物品槽中的物品堆
     */
    public ItemStack getItemStack() {
        return slot.getItemStack();
    }

    /**
     * 获取物品的价格。
     *
     * @return 物品的价格
     */
    public int getPrice() {
        return editBox.getInt();
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
        slot.render(guiGraphics, mouseX, mouseY, v);
        componentWidget.render(guiGraphics, mouseX, mouseY, v);
        editBox.render(guiGraphics, mouseX, mouseY, v);
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
        return List.of(slot, editBox);
    }

    @Override
    protected int contentHeight() {
        return height;
    }

    @Override
    protected double scrollRate() {
        return 0;
    }
}