package com.tksimeji.visualkit.markupextension.operator;

import com.tksimeji.visualkit.markupextension.MarkupExtensionException;
import com.tksimeji.visualkit.markupextension.context.Context;
import com.tksimeji.visualkit.markupextension.ast.*;
import org.jetbrains.annotations.NotNull;

public final class DivididedByOperator implements BinaryOperator<NumberNode<?>> {
    @Override
    public @NotNull String getOperator() {
        return "/";
    }

    @Override
    public @NotNull NumberNode<?> evaluate(final @NotNull Context context, final @NotNull Object left, final @NotNull Object right) {
        if (!(left instanceof Number leftNumber) || !(right instanceof Number rightNumber)) {
            throw new MarkupExtensionException(String.format("Invalid operand combination: %s / %s", left.getClass().getName(), right.getClass().getName()));
        }

        if (rightNumber.doubleValue() == 0) {
            throw new MarkupExtensionException("Division by zero is not possible.");
        }

        if (left instanceof Integer leftInteger && right instanceof Integer rightInteger && (leftInteger % rightInteger == 0)) {
            return new IntegerNumberNode(leftInteger / rightInteger);
        }

        double result = leftNumber.doubleValue() / rightNumber.doubleValue();

        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return new IntegerNumberNode((int) result);
        } else {
            return new DoubleNumberNode(result);
        }
    }
}
