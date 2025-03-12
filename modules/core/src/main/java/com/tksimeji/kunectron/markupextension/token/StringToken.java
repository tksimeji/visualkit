package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.NotNull;

final class StringToken extends TokenImpl {
    public StringToken(final @NotNull String value) {
        super(value);
    }

    @Override
    public boolean isString() {
        return true;
    }
}
