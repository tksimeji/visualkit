package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnvilGuiHooks extends IAnvilGuiHooks {
    @Override
    default @NotNull Player hookPlayer() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement hookGetFirstElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetFirstElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement hookGetSecondElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetSecondElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement hookGetResultElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetResultElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetPolicy(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetDefaultPolicy() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetPlayerDefaultPolicy() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetPolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
