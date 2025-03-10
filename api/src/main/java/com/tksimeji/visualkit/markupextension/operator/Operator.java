package com.tksimeji.visualkit.markupextension.operator;

import com.tksimeji.visualkit.markupextension.ast.AstNode;
import org.jetbrains.annotations.NotNull;

public interface Operator<T extends AstNode<?>> {
    @NotNull String getOperator();
}
