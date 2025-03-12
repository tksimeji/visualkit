package com.tksimeji.kunectron.markupextension.operator;

import com.tksimeji.kunectron.markupextension.ast.AstNode;
import org.jetbrains.annotations.NotNull;

public interface Operator<T extends AstNode<?>> {
    @NotNull String getOperator();
}
