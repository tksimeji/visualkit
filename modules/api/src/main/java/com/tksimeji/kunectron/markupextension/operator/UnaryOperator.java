package com.tksimeji.kunectron.markupextension.operator;

import com.tksimeji.kunectron.markupextension.ast.AstNode;
import com.tksimeji.kunectron.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public interface UnaryOperator<T extends AstNode<?>> extends Operator<T> {
    @NotNull T evaluate(final @NotNull Context<?> ctx, final @NotNull Object operand);
}
