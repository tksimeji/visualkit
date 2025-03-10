package com.tksimeji.visualkit.markupextension.operator;

import com.tksimeji.visualkit.markupextension.ast.BooleanNode;
import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public final class InequalityOperator implements BinaryOperator<BooleanNode> {
    @Override
    public @NotNull String getOperator() {
        return "!=";
    }

    @Override
    public @NotNull BooleanNode evaluate(final @NotNull Context<?> ctx, final @NotNull Object left, final @NotNull Object right) {
        return new BooleanNode(!left.equals(right));
    }
}
