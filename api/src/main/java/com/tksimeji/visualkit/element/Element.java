package com.tksimeji.visualkit.element;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.UUID;

public interface Element<T> {
    static @NotNull ItemElement item(final @NotNull ItemType type) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull ItemElement item(final @NotNull ItemStack itemStack) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull PlayerHeadElement playerHead() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable String name) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable UUID uuid) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable OfflinePlayer player) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable URL url) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull TradeElement trade(@NotNull ItemStack result, @NotNull ItemStack ingredient) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull TradeElement trade(@NotNull ItemStack result, @NotNull ItemStack ingredient1, @Nullable ItemStack ingredient2) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @NotNull T create();
}
