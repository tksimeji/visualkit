package com.tksimeji.visualkit.trade;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Deprecated(since = "1.0.0", forRemoval = true)
public record Requirement(@Nullable ItemStack item1, @Nullable ItemStack item2) {
}
