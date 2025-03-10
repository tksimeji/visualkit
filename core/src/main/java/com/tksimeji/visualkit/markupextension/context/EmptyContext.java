package com.tksimeji.visualkit.markupextension.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EmptyContext implements Context {
    @Override
    public @Nullable Object getState(final @NotNull String name) {
        return null;
    }

    @Override
    public @Nullable Object getFunction(final @NotNull String name, final @NotNull Object... args) {
        return null;
    }

    @Override
    public @Nullable Object getMember(final @NotNull String name) {
        return null;
    }
}
