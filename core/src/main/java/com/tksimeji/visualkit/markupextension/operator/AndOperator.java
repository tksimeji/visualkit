package com.tksimeji.visualkit.markupextension.operator;

import com.tksimeji.visualkit.markupextension.MarkupExtensionException;
import com.tksimeji.visualkit.markupextension.ast.BooleanNode;
import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public final class AndOperator implements BinaryOperator<BooleanNode> {
    @Override
    public @NotNull String getOperator() {
        return "&&";
    }

    @Override
    public @NotNull BooleanNode evaluate(final @NotNull Context context, final @NotNull Object left, final @NotNull Object right) {
        if (!(left instanceof Boolean leftBoolean) || !(right instanceof Boolean rightBoolean)) {
            throw new MarkupExtensionException(String.format("Invalid operand combination: %s && %s", left.getClass().getName(), right.getClass().getName()));
        }
        return new BooleanNode(leftBoolean && rightBoolean);
    }
}
