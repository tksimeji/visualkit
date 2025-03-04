package com.tksimeji.visualkit.element;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class TradeElementImpl implements TradeElement {
    private @NotNull ItemStack result;

    private @NotNull List<ItemStack> ingredients = List.of();

    private int maxUses = Integer.MAX_VALUE;

    public TradeElementImpl(final @NotNull ItemStack result) {
        this.result = result;
    }

    @Override
    public @NotNull ItemStack result() {
        return result;
    }

    @Override
    public @NotNull TradeElement result(final @NotNull ItemStack result) {
        this.result = result;
        return this;
    }

    @Override
    public @NotNull List<ItemStack> ingredients() {
        return ingredients.stream().toList();
    }

    @Override
    public @NotNull TradeElement ingredients(final @NotNull Collection<ItemStack> ingredients) {
        this.ingredients = ingredients.stream().toList();
        return this;
    }

    @Override
    public @NotNull TradeElement ingredients(final @NotNull ItemStack... ingredients) {
        return ingredients(Arrays.stream(ingredients).toList());
    }

    @Override
    public int maxUses() {
        return maxUses;
    }

    @Override
    public @NotNull TradeElement maxUses(final @Range(from = 0, to = Integer.MAX_VALUE) int maxUses) {
        this.maxUses = maxUses;
        return this;
    }
}
