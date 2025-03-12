package com.tksimeji.kunectron.markupextension.ast;

import com.tksimeji.kunectron.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public final class StringNode implements PrimitiveNode<String> {
    private final @NotNull String value;

    public StringNode(final @NotNull String value) {
        this.value = value;
    }

    @Override
    public @NotNull String getValue() {
        return value;
    }

    @Override
    public @NotNull String evaluate(@NotNull Context<?> ctx) {
        return value;
    }

    @Override
    public @NotNull String toString() {
        return value;
    }
}
