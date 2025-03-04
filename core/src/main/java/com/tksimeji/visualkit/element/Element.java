package com.tksimeji.visualkit.element;

import com.google.common.base.Preconditions;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Element {
    static @NotNull ItemElement item(final @NotNull ItemType type) {
        Preconditions.checkArgument(type != null, "Item type cannot be null.");
        return new ItemElementImpl(type);
    }

    static @NotNull ItemElement item(final @NotNull ItemStack itemStack) {
        Preconditions.checkArgument(itemStack != null, "Item stack cannot be null.");
        return new ItemStackElementImpl(itemStack);
    }

    static @NotNull PlayerHeadElement playerHead() {
        return new PlayerHeadElementImpl();
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable String name) {
        return playerHead().name(name);
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable UUID uuid) {
        return playerHead().uuid(uuid);
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable OfflinePlayer player) {
        return playerHead().player(player);
    }

    static @NotNull PlayerHeadElement playerHead(final @Nullable URL url) {
        return playerHead().url(url);
    }

    static @NotNull TradeElement trade(@NotNull ItemStack result) {
        return trade(result, List.of());
    }

    static @NotNull TradeElement trade(@NotNull ItemStack result, @Nullable Collection<ItemStack> ingredients) {
        Preconditions.checkArgument(result != null, "Trade result cannot be null.");
        TradeElement element = new TradeElementImpl(result);

        if (ingredients != null) {
            return element.ingredients(ingredients);
        } else {
            return element;
        }
    }

    static @NotNull TradeElement trade(@NotNull ItemStack result, @NotNull ItemStack... ingredients) {
        return trade(result, Arrays.stream(ingredients).toList());
    }
}
