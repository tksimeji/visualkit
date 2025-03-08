package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.TradeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

interface IMerchantGuiHooks extends Hooks {
    @Nullable TradeElement useGetElement(final int index);

    @NotNull List<TradeElement> useGetElements();

    void useSetElement(final int index, final @NotNull TradeElement element);

    void useAddElement(final @NotNull TradeElement element);

    void useRemoveElement(final int index);

    void useInsertElement(final int index, final @NotNull TradeElement element);
}
