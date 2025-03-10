package com.tksimeji.visualkit.markupextension.ast;

import com.tksimeji.visualkit.markupextension.context.Context;
import com.tksimeji.visualkit.markupextension.context.SimpleContext;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class MemberAccessNode implements AstNode<Object> {
    private final @NotNull AstNode<?> node;
    private final @NotNull String member;

    public MemberAccessNode(final @NotNull AstNode<?> node, final @NotNull String member) {
        this.node = node;
        this.member = member;
    }

    @Override
    public @NotNull Object evaluate(final @NotNull Context context) {
        return Optional.ofNullable(new SimpleContext(node.evaluate(context)).getMember(member)).orElse("null");
    }
}
