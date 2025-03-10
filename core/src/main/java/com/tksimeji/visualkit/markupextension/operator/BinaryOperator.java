package com.tksimeji.visualkit.markupextension.operator;

import com.tksimeji.visualkit.markupextension.context.Context;
import com.tksimeji.visualkit.markupextension.ast.AstNode;
import org.jetbrains.annotations.NotNull;

public interface BinaryOperator<T extends AstNode<?>> extends Operator<T> {
    @NotNull T evaluate(final @NotNull Context<?> ctx, final @NotNull Object left, final @NotNull Object right);
}
