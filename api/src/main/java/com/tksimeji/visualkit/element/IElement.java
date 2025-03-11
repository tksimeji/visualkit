package com.tksimeji.visualkit.element;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
interface IElement<T> {
    @NotNull T create();

    @NotNull Element<?> createCopy();
}
