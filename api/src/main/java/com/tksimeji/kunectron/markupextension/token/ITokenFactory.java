package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
interface ITokenFactory {
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
