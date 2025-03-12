package com.tksimeji.kunectron.markupextension.token;

final class DotToken extends TokenImpl {
    public DotToken() {
        super(".");
    }

    @Override
    public boolean isDot() {
        return true;
    }
}
