package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.element.TradeElement;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MerchantGuiHooks extends IMerchantGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable TradeElement useElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useElement(final int index, final @NotNull TradeElement element) {
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

    @Override
    default @NotNull List<TradeElement> useElements() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useClose() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
