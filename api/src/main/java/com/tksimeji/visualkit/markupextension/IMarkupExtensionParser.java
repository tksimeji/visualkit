package com.tksimeji.visualkit.markupextension;

import com.tksimeji.visualkit.markupextension.ast.AstNode;
import com.tksimeji.visualkit.markupextension.token.Token;
import com.tksimeji.visualkit.markupextension.token.Tokenizer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@ApiStatus.Internal
interface IMarkupExtensionParser {
    @NotNull AstNode<?> parse(final @NotNull String input);

    @NotNull AstNode<?> parse(final @NotNull String input, final @NotNull Tokenizer tokenizer);

    @NotNull AstNode<?> parse(final @NotNull Collection<Token> tokens);
}
