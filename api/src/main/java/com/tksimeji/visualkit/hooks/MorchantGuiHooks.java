package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.TradeElement;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MorchantGuiHooks extends Hooks {
    default @NotNull TradeElement useElement(@NotNull ItemStack result) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default @Nullable TradeElement useGetElement(int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useSetElement(int index, @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useAddElement(@NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useRemoveElement(int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useRemoveElements() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default int useSize() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default boolean useIsEmpty() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default boolean useIsNotEmpty() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
