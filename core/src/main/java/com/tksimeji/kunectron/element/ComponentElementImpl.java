package com.tksimeji.kunectron.element;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.markupextension.MarkupExtensionSupport;
import com.tksimeji.kunectron.markupextension.context.Context;
import com.tksimeji.kunectron.util.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComponentElementImpl implements ComponentElement, MarkupExtensionSupport {
    private @NotNull Component source;

    private @Nullable Context<?> markupExtensionContext;

    public ComponentElementImpl(final @NotNull ComponentLike component) {
        source = component.asComponent();
    }

    @Override
    public @Nullable Context<?> getContext() {
        return markupExtensionContext;
    }

    @Override
    public void setContext(final @Nullable Context<?> ctx) {
        markupExtensionContext = ctx;
    }

    @Override
    public @NotNull ComponentElement source(final @NotNull ComponentLike source) {
        Preconditions.checkArgument(source != null, "Source cannot be null.");
        this.source = source.asComponent();
        return this;
    }

    @Override
    public @NotNull Component source() {
        return source;
    }

    @Override
    public @NotNull Component create() {
        if (!Components.hasMarkupExtension(source) || markupExtensionContext == null) {
            return source;
        }
        return Components.markupExtension(source, markupExtensionContext);
    }

    @Override
    public @NotNull ComponentElement createCopy() {
        ComponentElementImpl copy = new ComponentElementImpl(source);
        copy.markupExtensionContext = markupExtensionContext;
        return copy;
    }

    @Override
    public @NotNull Component asComponent() {
        return create();
    }
}
