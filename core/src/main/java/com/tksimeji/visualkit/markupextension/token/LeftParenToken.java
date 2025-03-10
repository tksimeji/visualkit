package com.tksimeji.visualkit.markupextension.token;

final class LeftParenToken extends TokenImpl {
    public LeftParenToken() {
        super("(");
    }

    @Override
    public boolean isLeftParen() {
        return true;
    }
}
