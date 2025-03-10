package com.tksimeji.visualkit.markupextension.ast;

import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
interface IAstNode<T> {
    @NotNull T evaluate(final @NotNull Context<?> ctx);

    @NotNull Object evaluateDeep(final @NotNull Context<?> ctx);
}
