package com.tksimeji.visualkit.markupextension.operator;

import com.tksimeji.visualkit.markupextension.ast.AstNode;
import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public interface UnaryOperator<T extends AstNode<?>> extends Operator<T> {
    @NotNull T evaluate(final @NotNull Context context, final @NotNull Object operand);
}
