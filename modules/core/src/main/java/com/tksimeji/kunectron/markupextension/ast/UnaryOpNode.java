package com.tksimeji.kunectron.markupextension.ast;

import com.tksimeji.kunectron.markupextension.context.Context;
import com.tksimeji.kunectron.markupextension.operator.UnaryOperator;
import org.jetbrains.annotations.NotNull;

public final class UnaryOpNode implements AstNode<AstNode<?>> {
    private final @NotNull UnaryOperator<?> operator;

    private final @NotNull AstNode<?> operand;

    public UnaryOpNode(final @NotNull UnaryOperator<?> operator, final @NotNull AstNode<?> operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public @NotNull AstNode<?> evaluate(@NotNull Context<?> ctx) {
        return operator.evaluate(ctx, operand.evaluate(ctx));
    }
}
