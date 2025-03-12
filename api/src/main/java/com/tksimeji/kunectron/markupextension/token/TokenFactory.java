package com.tksimeji.kunectron.markupextension.token;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public interface TokenFactory extends ITokenFactory {
    static @NotNull TokenFactory create() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
