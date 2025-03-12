package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.hooks.AnvilGuiHooks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface AnvilGuiBuilder extends ItemContainerGuiBuilder<AnvilGuiBuilder, AnvilGuiHooks> {
    @Contract("_ -> this")
    @NotNull AnvilGuiBuilder firstElement(final @NotNull ItemElement firstElement);

    @Contract("_ -> this")
    @NotNull AnvilGuiBuilder secondElement(final @NotNull ItemElement secondElement);

    @Contract("_ -> this")
    @NotNull AnvilGuiBuilder resultElement(final @NotNull ItemElement resultElement);
}
