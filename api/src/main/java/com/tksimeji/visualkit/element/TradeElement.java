package com.tksimeji.visualkit.element;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface TradeElement extends Element {
    @NotNull ItemStack result();

    @NotNull TradeElement result(@NotNull ItemStack result);

    @NotNull Collection<ItemStack> ingredients();

    @NotNull TradeElement ingredients(@NotNull Collection<ItemStack> ingredients);

    @NotNull TradeElement ingredients(@NotNull ItemStack... ingredients);

    int maxUses();

    @NotNull TradeElement maxUses(int maxUses);
}
