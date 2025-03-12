package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.hooks.ContainerGuiHooks;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

interface ContainerGuiBuilder<B extends ContainerGuiBuilder<B, H>, H extends ContainerGuiHooks> extends GuiBuilder<B, H> {
    @Contract("_ -> this")
    @NotNull B title(final @NotNull ComponentLike title);

    @NotNull H build(final @NotNull Player player);
}
