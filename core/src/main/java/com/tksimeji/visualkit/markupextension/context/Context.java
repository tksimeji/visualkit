package com.tksimeji.visualkit.markupextension.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Context<T> {
    static <T> @NotNull Context<T> context(final @NotNull T object) {
        return new SimpleContext<>(object);
    }

    static <T> @NotNull MutableContext<T> mutable(final @NotNull T object) {
        return new SimpleMutableContext<>(object);
    }

    @NotNull T getObject();

    @Nullable Object getState(final @NotNull String name);

    @Nullable Object getFunction(final @NotNull String name, final @NotNull Object... args);

    @Nullable Object getMember(final @NotNull String name);
}
