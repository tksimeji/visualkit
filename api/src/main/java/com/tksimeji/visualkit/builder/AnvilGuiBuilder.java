package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.hooks.AnvilGuiHooks;
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
