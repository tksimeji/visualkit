package com.tksimeji.kunectron.markupextension.token;

final class RightParenToken extends TokenImpl {
    public RightParenToken() {
        super(")");
    }

    @Override
    public boolean isRightParen() {
        return true;
    }
}
