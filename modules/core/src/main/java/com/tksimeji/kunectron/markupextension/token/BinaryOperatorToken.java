package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.NotNull;

public final class BinaryOperatorToken extends TokenImpl {
    public BinaryOperatorToken(final @NotNull String value) {
        super(value);
    }

    @Override
    public boolean isBinaryOperator() {
        return true;
    }
}
