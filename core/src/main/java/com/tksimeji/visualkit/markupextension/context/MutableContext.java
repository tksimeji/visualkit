package com.tksimeji.visualkit.markupextension.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MutableContext extends Context {
    void setState(final @NotNull String name, final @Nullable Object value);
}
