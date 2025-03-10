package com.tksimeji.visualkit.markupextension.token;

final class DotToken extends TokenImpl {
    public DotToken() {
        super(".");
    }

    @Override
    public boolean isDot() {
        return true;
    }
}
