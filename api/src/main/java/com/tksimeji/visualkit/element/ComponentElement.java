package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.markupextension.MarkupExtensionSupport;
import com.tksimeji.visualkit.markupextension.context.Context;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentElement extends ComponentLike, Element<Component>, MarkupExtensionSupport {
    @NotNull Component source();

    @Contract("_ -> this")
    @NotNull ComponentElement source(final @NotNull ComponentLike source);

    @Nullable Context<?> context();

    @Contract("_ -> this")
    @NotNull ComponentElement context(final @Nullable Context<?> ctx);

    @Override
    @NotNull ComponentElement createCopy();
}
