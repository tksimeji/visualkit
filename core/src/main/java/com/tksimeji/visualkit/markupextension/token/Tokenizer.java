package com.tksimeji.visualkit.markupextension.token;

import org.jetbrains.annotations.NotNull;

public interface Tokenizer extends ITokenizer {
    static @NotNull Tokenizer create() {
        return new TokenizerImpl();
    }
}
