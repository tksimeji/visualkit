package com.tksimeji.kunectron.markupextension.token;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public interface Tokenizer extends ITokenizer {
    static @NotNull Tokenizer create() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
