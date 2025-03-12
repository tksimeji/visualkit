package com.tksimeji.kunectron.markupextension.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MutableContext<T> extends Context<T> {
    void setState(final @NotNull String name, final @Nullable Object value);
}
