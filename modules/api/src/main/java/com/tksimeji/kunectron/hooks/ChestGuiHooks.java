package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface ChestGuiHooks extends Hooks, IChestGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement useElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useElement(final int index, final @NotNull ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useClearElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull Map<Integer, ItemElement> useElements() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy usePolicy(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void usePolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy useDefaultPolicy() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy usePlayerDefaultPolicy() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void usePlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useClose() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
