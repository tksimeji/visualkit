package com.tksimeji.visualkit.trade;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public record Requirement(@Nullable ItemStack item1, @Nullable ItemStack item2) {
}
