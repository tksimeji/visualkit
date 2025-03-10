package com.tksimeji.visualkit.markupextension.token;

import org.jetbrains.annotations.NotNull;

public interface TokenFactory extends ITokenFactory {
    static @NotNull TokenFactory create() {
        return new TokenFactoryImpl();
    }
}
