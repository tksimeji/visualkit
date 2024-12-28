package com.tksimeji.visualkit.product;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class Price {
    private @Nullable ItemStack item1;
    private @Nullable ItemStack item2;

    Price(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public @Nullable ItemStack getItem1() {
        return item1;
    }

    public void setItem1(@Nullable ItemStack item1) {
        this.item1 = item1;
    }

    public @Nullable ItemStack getItem2() {
        return item2;
    }

    public void setItem2(@Nullable ItemStack item2) {
        this.item2 = item2;
    }
}
