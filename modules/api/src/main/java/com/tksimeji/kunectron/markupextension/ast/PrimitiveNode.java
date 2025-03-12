package com.tksimeji.kunectron.markupextension.ast;

import org.jetbrains.annotations.NotNull;

public interface PrimitiveNode<T> extends AstNode<T> {
    @NotNull T getValue();
}
