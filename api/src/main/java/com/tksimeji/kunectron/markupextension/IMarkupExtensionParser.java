package com.tksimeji.kunectron.markupextension;

import com.tksimeji.kunectron.markupextension.ast.AstNode;
import com.tksimeji.kunectron.markupextension.token.Token;
import com.tksimeji.kunectron.markupextension.token.Tokenizer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@ApiStatus.Internal
interface IMarkupExtensionParser {
    @NotNull AstNode<?> parse(final @NotNull String input);

    @NotNull AstNode<?> parse(final @NotNull String input, final @NotNull Tokenizer tokenizer);

    @NotNull AstNode<?> parse(final @NotNull Collection<Token> tokens);
}
