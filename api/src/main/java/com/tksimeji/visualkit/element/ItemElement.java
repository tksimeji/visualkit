package com.tksimeji.visualkit.element;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ItemElement extends Element {
    @NotNull ItemType type();

    @NotNull Component title();

    @NotNull ItemElement title(@NotNull ComponentLike title);

    @NotNull ItemElement title(@NotNull String title);

    @NotNull Collection<Component> lore();

    @NotNull ItemElement lore(@NotNull Collection<ComponentLike> components);

    @NotNull ItemElement lore(@NotNull ComponentLike... components);

    @NotNull ItemElement lore(@NotNull String... strings);

    int amount();

    @NotNull ItemElement amount(int amount);

    int customModelData();

    @NotNull ItemElement customModelData(int customModelData);

    boolean aura();

    @NotNull ItemElement aura(boolean aura);

    @Nullable Sound sound();

    float soundVolume();

    float soundPitch();

    @NotNull ItemElement sound(@Nullable Sound sound);

    @NotNull ItemElement sound(@NotNull Sound sound, float volume, float pitch);

    @NotNull ItemElement handler(@Nullable Handler1 handler);

    @NotNull ItemElement handler(@Nullable Handler2 handler);

    interface Handler {
    }

    interface Handler1 extends Handler {
        void onClick();
    }

    interface Handler2 extends Handler {
        void onClick(int index, @NotNull Action action, @NotNull Mouse mouse);
    }

    enum Action {
        SINGLE_CLICK,
        DOUBLE_CLICK,
        SHIFT_CLICK
    }

    enum Mouse {
        LEFT,
        RIGHT
    }
}
