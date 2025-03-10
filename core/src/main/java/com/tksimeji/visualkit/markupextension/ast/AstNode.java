package com.tksimeji.visualkit.markupextension.ast;

import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

public interface AstNode<T> {
    @NotNull T evaluate(final @NotNull Context context);

    default @NotNull Object evaluateDeep(final @NotNull Context context) {
        if (this instanceof PrimitiveNode<?> primitiveNode) {
            return primitiveNode.getValue();
        }

        if (this instanceof BinaryOpNode binaryOpNode) {
            AstNode<?> node = binaryOpNode.evaluate(context);
            while (node instanceof BinaryOpNode) {
                node = ((BinaryOpNode) node).evaluate(context);
            }
            return node.evaluateDeep(context);
        }

        return evaluate(context);
    }
}
