package org.hiedacamellia.immersiveui.util.holder;

import net.minecraft.world.item.ItemStack;

public class ItemStackHolder implements IValueHolder<ItemStack>{
    private ItemStack itemStack;

    public ItemStackHolder() {
        this.itemStack = ItemStack.EMPTY;
    }

    public ItemStackHolder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    @Override
    public void set(ItemStack value) {
        this.itemStack = value;
    }

    @Override
    public ItemStack get() {
        return itemStack;
    }
}
