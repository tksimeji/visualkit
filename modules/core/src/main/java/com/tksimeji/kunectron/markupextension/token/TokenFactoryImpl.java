package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.NotNull;

final class TokenFactoryImpl implements TokenFactory {
    @Override
    public @NotNull Token createBoolean(final @NotNull String value) {
        return new BooleanToken(value);
    }

    @Override
    public @NotNull Token createNumber(final @NotNull String value) {
        return new NumberToken(value);
    }

    @Override
    public @NotNull Token createString(final @NotNull String value) {
        return new StringToken(value);
    }

    @Override
    public @NotNull Token createBinaryOperator(final @NotNull String value) {
        return new BinaryOperatorToken(value);
    }

    @Override
    public @NotNull Token createUnaryOperator(final @NotNull String value) {
        return new UnaryOperatorToken(value);
    }

    @Override
    public @NotNull Token createIdentifier(final @NotNull String value) {
        return new IdentifierToken(value);
    }

    @Override
    public @NotNull Token createDot() {
        return new DotToken();
    }

    @Override
    public @NotNull Token createLeftParen() {
        return new LeftParenToken();
    }

    @Override
    public @NotNull Token createRightParen() {
        return new RightParenToken();
    }

    @Override
    public @NotNull Token createComma() {
        return new CommaToken();
    }
}
