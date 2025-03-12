package com.tksimeji.kunectron.markupextension.token;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ApiStatus.Internal
interface ITokenizer {
    @NotNull TokenFactory getFactory();

    void setFactory(final @NotNull TokenFactory factory);

    @NotNull List<Token> tokenize(final @NotNull String input);
}
