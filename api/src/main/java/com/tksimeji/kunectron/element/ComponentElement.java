package com.tksimeji.kunectron.element;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ComponentElement extends ComponentLike, Element<Component> {
    @NotNull Component source();

    @Contract("_ -> this")
    @NotNull ComponentElement source(final @NotNull ComponentLike source);

    @Override
    @NotNull ComponentElement createCopy();
}
