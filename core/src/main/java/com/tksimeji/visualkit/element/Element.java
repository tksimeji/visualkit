package com.tksimeji.visualkit.element;

import com.google.common.base.Preconditions;
import com.tksimeji.visualkit.markupextension.context.Context;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.UUID;

public interface Element<T> extends IElement<T> {
    static @NotNull ComponentElement component() {
        return component(Component.empty());
    }

    static @NotNull ComponentElement component(final @NotNull ComponentLike component) {
        return new ComponentElementImpl(component);
    }

    static @NotNull ComponentElement component(final @NotNull ComponentLike component, final @Nullable Context<?> ctx) {
        return component(component).context(ctx);
    }

    static @NotNull ComponentElement component(final @NotNull ComponentLike component, final @Nullable Object object) {
        return component(component, object != null ? Context.context(object) : null);
    }

    static @NotNull ItemElement item(final @NotNull ItemType type) {
        Preconditions.checkArgument(type != null, "Item type cannot be null.");
        return new ItemElementImpl(type);
    }

    static @NotNull ItemElement item(final @NotNull ItemStack itemStack) {
        Preconditions.checkArgument(itemStack != null, "Item stack cannot be null.");
        return new ItemElementImpl(itemStack.clone());
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

    static @NotNull TradeElement trade(@NotNull ItemStack result, @NotNull ItemStack ingredient) {
        return trade(result, ingredient, null);
    }

    static @NotNull TradeElement trade(@NotNull ItemStack result, @NotNull ItemStack ingredient1, @Nullable ItemStack ingredient2) {
        Preconditions.checkArgument(result != null, "Trade result cannot be null.");
        Preconditions.checkArgument(ingredient1 != null, "Ingredient-1 cannot be null.");
        return new TradeElementImpl(result, ingredient1, ingredient2);
    }
}
