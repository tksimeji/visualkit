package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.hooks.ContainerGuiHooks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

public abstract class ContainerGuiBuilderImpl<B extends ContainerGuiBuilder<B, H>, H extends ContainerGuiHooks> extends IGuiBuilderImpl<B, H> implements ContainerGuiBuilder<B, H> {
    protected @NotNull Component title = Component.empty();

    @Override
    public @NotNull B title(@NotNull ComponentLike title) {
        this.title = title.asComponent();
        return (B) this;
    }
}
