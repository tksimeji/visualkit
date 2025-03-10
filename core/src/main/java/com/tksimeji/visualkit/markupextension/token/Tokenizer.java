package com.tksimeji.visualkit.markupextension.token;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Tokenizer {
    static @NotNull Tokenizer create() {
        return new TokenizerImpl();
    }

    @NotNull TokenFactory getFactory();

    void setFactory(final @NotNull TokenFactory factory);

    @NotNull List<Token> tokenize(final @NotNull String input);
}
