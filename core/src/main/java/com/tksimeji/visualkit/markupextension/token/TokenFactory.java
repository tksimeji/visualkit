package com.tksimeji.visualkit.markupextension.token;

import org.jetbrains.annotations.NotNull;

public interface TokenFactory {
    static @NotNull TokenFactory create() {
        return new TokenFactoryImpl();
    }

    @NotNull Token createBoolean(final @NotNull String value);

    @NotNull Token createNumber(final @NotNull String value);

    @NotNull Token createString(final @NotNull String value);

    @NotNull Token createBinaryOperator(final @NotNull String value);

    @NotNull Token createUnaryOperator(final @NotNull String value);

    @NotNull Token createIdentifier(final @NotNull String value);

    @NotNull Token createDot();

    @NotNull Token createLeftParen();

    @NotNull Token createRightParen();

    @NotNull Token createComma();
}
