package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.element.TradeElement;
import com.tksimeji.visualkit.hooks.MerchantGuiHooks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface MerchantGuiBuilder extends ContainerGuiBuilder<MerchantGuiBuilder, MerchantGuiHooks> {
    @Contract("_ -> this")
    @NotNull MerchantGuiBuilder element(final @NotNull TradeElement element);
}
