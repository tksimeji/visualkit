package com.tksimeji.visualkit.markupextension.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Context {
    static @NotNull Context context(final @NotNull Object object) {
        return new SimpleContext(object);
    }

    static @NotNull MutableContext mutable(final @NotNull Object object) {
        return new SimpleMutableContext(object);
    }

    static @NotNull Context empty() {
        return new EmptyContext();
    }

    @Nullable Object getState(final @NotNull String name);

    @Nullable Object getFunction(final @NotNull String name, final @NotNull Object... args);

    @Nullable Object getMember(final @NotNull String name);
}
