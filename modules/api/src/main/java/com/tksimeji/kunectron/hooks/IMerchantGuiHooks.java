package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.element.TradeElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ApiStatus.Internal
interface IMerchantGuiHooks extends ContainerGuiHooks {
    @Nullable TradeElement useElement(final int index);

    void useElement(final int index, final @NotNull TradeElement element);

    void useAddElement(final @NotNull TradeElement element);

    void useRemoveElement(final int index);

    void useInsertElement(final int index, final @NotNull TradeElement element);

    @NotNull List<TradeElement> useElements();
}
