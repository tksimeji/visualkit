package com.tksimeji.kunectron.markupextension.operator;

import com.tksimeji.kunectron.markupextension.ast.BooleanNode;
import com.tksimeji.kunectron.markupextension.context.Context;
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
