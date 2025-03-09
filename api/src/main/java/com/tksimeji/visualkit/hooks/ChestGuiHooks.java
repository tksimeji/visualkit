package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
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
    default @Nullable ItemElement useGetElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull Map<Integer, ItemElement> useGetElements() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetElement(final int index, final @NotNull ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useClearElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy useGetPolicy(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetPolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy useGetDefaultPolicy() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull ItemSlotPolicy useGetPlayerDefaultPolicy() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useClose() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
