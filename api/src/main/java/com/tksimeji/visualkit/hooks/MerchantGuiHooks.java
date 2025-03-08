package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.TradeElement;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MerchantGuiHooks extends IMerchantGuiHooks {
    @Override
    default @Nullable TradeElement useGetElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull List<TradeElement> useGetElements() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetElement(final int index, final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useAddElement(final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useRemoveElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useInsertElement(final int index, final @NotNull TradeElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
