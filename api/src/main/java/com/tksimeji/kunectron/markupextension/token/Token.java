package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.NotNull;

public interface Token {
    @NotNull String getValue();

    boolean isBoolean();

    boolean isNumber();

    boolean isString();

    boolean isBinaryOperator();

    boolean isUnaryOperator();

    boolean isIdentifier();

    boolean isDot();

    boolean isLeftParen();

    boolean isRightParen();

    boolean isComma();
}
