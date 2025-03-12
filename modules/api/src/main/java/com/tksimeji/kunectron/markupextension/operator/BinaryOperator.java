package com.tksimeji.kunectron.markupextension.operator;

import com.tksimeji.kunectron.markupextension.context.Context;
import com.tksimeji.kunectron.markupextension.ast.AstNode;
import org.jetbrains.annotations.NotNull;

public interface BinaryOperator<T extends AstNode<?>> extends Operator<T> {
    @NotNull T evaluate(final @NotNull Context<?> ctx, final @NotNull Object left, final @NotNull Object right);
}
