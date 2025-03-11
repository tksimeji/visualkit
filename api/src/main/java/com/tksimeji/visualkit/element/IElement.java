package com.tksimeji.visualkit.element;

import org.jetbrains.annotations.NotNull;

interface IElement<T> {
    @NotNull T create();

    @NotNull Element<?> createCopy();
}
