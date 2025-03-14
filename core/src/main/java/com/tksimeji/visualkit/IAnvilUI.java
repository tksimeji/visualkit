package com.tksimeji.visualkit;

import org.bukkit.Material;
import org.bukkit.inventory.AnvilInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IAnvilUI extends IContainerUI<AnvilInventory> {
    default @NotNull Material dummy() {
        return Material.PAPER;
    }

    default @Nullable String placeholder() {
        return "";
    }

    default void onTyped(@NotNull String string) {}
}
