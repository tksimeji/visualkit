package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.element.TradeElement;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MerchantGuiHooks extends IMerchantGuiHooks {
    @Override
    default @Nullable TradeElement hookGetElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull List<TradeElement> hookGetElements() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetElement(final int index, final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookAddElement(final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookRemoveElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookInsertElement(final int index, final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
