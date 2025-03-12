package com.tksimeji.kunectron.markupextension.operator;

import com.tksimeji.kunectron.markupextension.MarkupExtensionException;
import com.tksimeji.kunectron.markupextension.ast.BooleanNode;
import com.tksimeji.kunectron.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public final class AndOperator implements BinaryOperator<BooleanNode> {
    @Override
    public @NotNull String getOperator() {
        return "&&";
    }

    @Override
    public @NotNull BooleanNode evaluate(final @NotNull Context<?> ctx, final @NotNull Object left, final @NotNull Object right) {
        if (!(left instanceof Boolean leftBoolean) || !(right instanceof Boolean rightBoolean)) {
            throw new MarkupExtensionException(String.format("Invalid operand combination: %s && %s", left.getClass().getName(), right.getClass().getName()));
        }
        return new BooleanNode(leftBoolean && rightBoolean);
    }
}
