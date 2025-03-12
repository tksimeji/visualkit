package com.tksimeji.kunectron.markupextension.operator;

import com.tksimeji.kunectron.markupextension.MarkupExtensionException;
import com.tksimeji.kunectron.markupextension.context.Context;
import com.tksimeji.kunectron.markupextension.ast.*;
import org.jetbrains.annotations.NotNull;

public class MinusOperator implements BinaryOperator<NumberNode<?>> {
    @Override
    public @NotNull String getOperator() {
        return "-";
    }

    @Override
    public @NotNull NumberNode<?> evaluate(final @NotNull Context<?> ctx, final @NotNull Object left, final @NotNull Object right) {
        if (!(left instanceof Number leftNumber) || !(right instanceof Number rightNumber)) {
            throw new MarkupExtensionException(String.format("Invalid operand combination: %s - %s", left.getClass().getName(), right.getClass().getName()));
        }

        if (left instanceof Integer leftInteger && right instanceof Integer rightInteger) {
            return new IntegerNumberNode(leftInteger - rightInteger);
        }

        return new DoubleNumberNode(leftNumber.doubleValue() - rightNumber.doubleValue());
    }
}
