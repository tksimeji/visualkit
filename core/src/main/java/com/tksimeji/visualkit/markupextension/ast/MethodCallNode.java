package com.tksimeji.visualkit.markupextension.ast;

import com.tksimeji.visualkit.markupextension.context.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class MethodCallNode implements AstNode<Object> {
    private final @NotNull String name;

    private final @NotNull List<AstNode<?>> args;

    public MethodCallNode(final @NotNull String name, final @NotNull Collection<AstNode<?>> args) {
        this.name = name;
        this.args = new ArrayList<>(args);
    }

    @Override
    public @NotNull Object evaluate(final @NotNull Context context) {
        Object[] args = this.args.stream().map(argument -> argument.evaluateDeep(context)).toArray();
        return Optional.ofNullable(context.getFunction(name, args)).orElse("null");
    }
}
