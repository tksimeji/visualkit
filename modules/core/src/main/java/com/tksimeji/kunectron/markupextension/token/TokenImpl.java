package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.NotNull;

abstract class TokenImpl implements Token {
    protected final String value;

    public TokenImpl(final @NotNull String value) {
        this.value = value;
    }

    @Override
    public @NotNull String getValue() {
        return value;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public boolean isBinaryOperator() {
        return false;
    }

    @Override
    public boolean isUnaryOperator() {
        return false;
    }

    @Override
    public boolean isIdentifier() {
        return false;
    }

    @Override
    public boolean isDot() {
        return false;
    }

    @Override
    public boolean isLeftParen() {
        return false;
    }

    @Override
    public boolean isRightParen() {
        return false;
    }

    @Override
    public boolean isComma() {
        return false;
    }
}
