package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.TradeElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ApiStatus.Internal
interface IMerchantGuiHooks extends ContainerGuiHooks {
    @Nullable TradeElement hookGetElement(final int index);

    @NotNull List<TradeElement> hookGetElements();

    void hookSetElement(final int index, final @NotNull TradeElement element);

    void hookAddElement(final @NotNull TradeElement element);

    void hookRemoveElement(final int index);

    void hookInsertElement(final int index, final @NotNull TradeElement element);
}
