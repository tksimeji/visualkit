package com.tksimeji.kunectron.markupextension.context;

import org.jetbrains.annotations.NotNull;

public interface Context<T> extends IContext<T> {
    static <T> @NotNull Context<T> context(final @NotNull T object) {
        return new ContextImpl<>(object);
    }

    static <T> @NotNull MutableContext<T> mutable(final @NotNull T object) {
        return new MutableContextImpl<>(object);
    }
}
