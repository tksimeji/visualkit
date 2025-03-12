package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.hooks.ScoreboardGuiHooks;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ScoreboardGuiBuilder extends GuiBuilder<ScoreboardGuiBuilder, ScoreboardGuiHooks> {
    @Contract("_ -> this")
    @NotNull ScoreboardGuiBuilder title(final @NotNull ComponentLike title);

    @Contract("_ -> this")
    @NotNull ScoreboardGuiBuilder line(final @NotNull ComponentLike line);

    @NotNull ScoreboardGuiHooks build();
}
