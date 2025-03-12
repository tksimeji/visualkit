package com.tksimeji.kunectron.markupextension.operator;

import com.tksimeji.kunectron.markupextension.MarkupExtensionException;
import com.tksimeji.kunectron.markupextension.ast.BooleanNode;
import com.tksimeji.kunectron.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public final class NotOperator implements UnaryOperator<BooleanNode> {
    @Override
    public @NotNull String getOperator() {
        return "!";
    }

    @Override
    public @NotNull BooleanNode evaluate(final @NotNull Context<?> ctx, final @NotNull Object operand) {
        if (!(operand instanceof Boolean booleanOperand)) {
            throw new MarkupExtensionException("Invalid operand: !" + operand.getClass().getName());
        }

        return new BooleanNode(!booleanOperand);
    }
}
