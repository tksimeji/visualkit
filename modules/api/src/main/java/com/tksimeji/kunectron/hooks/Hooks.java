package com.tksimeji.kunectron.hooks;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Hooks {
    default void useState(final @NotNull String name, final @Nullable Object value) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
