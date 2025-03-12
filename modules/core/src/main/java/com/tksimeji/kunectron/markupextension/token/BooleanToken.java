package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.NotNull;

final class BooleanToken extends TokenImpl {
    public BooleanToken(final @NotNull String value) {
        super(value);
    }

    @Override
    public boolean isBoolean() {
        return true;
    }
}
