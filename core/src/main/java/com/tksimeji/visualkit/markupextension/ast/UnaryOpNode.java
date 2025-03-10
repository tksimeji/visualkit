package com.tksimeji.visualkit.markupextension.ast;

import com.tksimeji.visualkit.markupextension.context.Context;
import com.tksimeji.visualkit.markupextension.operator.UnaryOperator;
import org.jetbrains.annotations.NotNull;

public final class UnaryOpNode implements AstNode<AstNode<?>> {
    private final @NotNull UnaryOperator<?> operator;

    private final @NotNull AstNode<?> operand;

    public UnaryOpNode(final @NotNull UnaryOperator<?> operator, final @NotNull AstNode<?> operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public @NotNull AstNode<?> evaluate(@NotNull Context context) {
        return operator.evaluate(context, operand.evaluate(context));
    }
}
