package com.tksimeji.visualkit.markupextension.ast;

import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public final class BooleanNode implements PrimitiveNode<Boolean> {
    private final boolean value;

    public BooleanNode(final boolean value) {
        this.value = value;
    }

    public BooleanNode(final @NotNull String value) {
        this(Boolean.parseBoolean(value));
    }

    @Override
    public @NotNull Boolean getValue() {
        return value;
    }

    @Override
    public @NotNull Boolean evaluate(final @NotNull Context context) {
        return value;
    }
}
