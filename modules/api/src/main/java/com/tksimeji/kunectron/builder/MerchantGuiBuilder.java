package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.element.TradeElement;
import com.tksimeji.kunectron.hooks.MerchantGuiHooks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface MerchantGuiBuilder extends ContainerGuiBuilder<MerchantGuiBuilder, MerchantGuiHooks> {
    @Contract("_ -> this")
    @NotNull MerchantGuiBuilder element(final @NotNull TradeElement element);
}
