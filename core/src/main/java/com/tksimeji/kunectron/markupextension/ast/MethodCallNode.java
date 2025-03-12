package com.tksimeji.kunectron.markupextension.ast;

import com.tksimeji.kunectron.markupextension.context.Context;
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
    public @NotNull Object evaluate(final @NotNull Context<?> ctx) {
        Object[] args = this.args.stream().map(argument -> argument.evaluateDeep(ctx)).toArray();
        return Optional.ofNullable(ctx.getFunction(name, args)).orElse("null");
    }
}
