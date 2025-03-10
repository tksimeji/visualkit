package com.tksimeji.visualkit.markupextension.operator;

import com.tksimeji.visualkit.markupextension.MarkupExtensionException;
import com.tksimeji.visualkit.markupextension.ast.BooleanNode;
import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public final class NotOperator implements UnaryOperator<BooleanNode> {
    @Override
    public @NotNull String getOperator() {
        return "!";
    }

    @Override
    public @NotNull BooleanNode evaluate(final @NotNull Context context, final @NotNull Object operand) {
        if (!(operand instanceof Boolean booleanOperand)) {
            throw new MarkupExtensionException("Invalid operand: !" + operand.getClass().getName());
        }

        return new BooleanNode(!booleanOperand);
    }
}
