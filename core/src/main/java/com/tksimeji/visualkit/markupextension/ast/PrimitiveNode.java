package com.tksimeji.visualkit.markupextension.ast;

import org.jetbrains.annotations.NotNull;

public interface PrimitiveNode<T> extends AstNode<T> {
    @NotNull T getValue();
}
