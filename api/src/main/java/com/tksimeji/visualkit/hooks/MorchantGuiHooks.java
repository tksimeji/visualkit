package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.TradeElement;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MorchantGuiHooks extends Hooks {
    default @Nullable TradeElement useGetElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useSetElement(final int index, final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useAddElement(final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useRemoveElement(final int index) {
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
