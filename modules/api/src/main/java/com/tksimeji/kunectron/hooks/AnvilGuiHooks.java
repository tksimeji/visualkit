package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnvilGuiHooks extends IAnvilGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement useFirstElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useFirstElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement useSecondElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSecondElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement useResultElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useResultElement(@Nullable ItemElement element) {
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
}
