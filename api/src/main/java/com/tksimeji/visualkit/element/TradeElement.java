package com.tksimeji.visualkit.element;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collection;

public interface TradeElement extends Element {
    @NotNull ItemStack result();

    @Contract("_ -> this")
    @NotNull TradeElement result(final @NotNull ItemStack result);

    @NotNull Collection<ItemStack> ingredients();

    @Contract("_ -> this")
    @NotNull TradeElement ingredients(final @NotNull Collection<ItemStack> ingredients);

    @Contract("_ -> this")
    @NotNull TradeElement ingredients(final @NotNull ItemStack... ingredients);

    int maxUses();

    @Contract("_ -> this")
    @NotNull TradeElement maxUses(final @Range(from = 0, to = Integer.MAX_VALUE) int maxUses);
}
