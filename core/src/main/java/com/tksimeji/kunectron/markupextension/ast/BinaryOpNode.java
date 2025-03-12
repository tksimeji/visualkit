package com.tksimeji.kunectron.markupextension.ast;

import com.tksimeji.kunectron.markupextension.context.Context;
import com.tksimeji.kunectron.markupextension.operator.BinaryOperator;
import org.jetbrains.annotations.NotNull;

public final class BinaryOpNode implements AstNode<AstNode<?>> {
    private final @NotNull AstNode<?> left;
    private final @NotNull BinaryOperator<?> operator;
    private final @NotNull AstNode<?> right;

    public BinaryOpNode(final @NotNull AstNode<?> left, final @NotNull BinaryOperator<?> operator, final @NotNull AstNode<?> right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public @NotNull AstNode<?> evaluate(@NotNull Context<?> ctx) {
        return operator.evaluate(ctx, left.evaluateDeep(ctx), right.evaluateDeep(ctx));
    }
}
