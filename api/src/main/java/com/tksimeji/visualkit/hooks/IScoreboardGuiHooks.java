package com.tksimeji.visualkit.hooks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@ApiStatus.Internal
interface IScoreboardGuiHooks extends Hooks {
    @NotNull Set<Player> hookGetPlayers();

    void hookAddPlayer(final @NotNull Player player);

    void hookRemovePlayer(final @NotNull Player player);

    boolean hookIsPlayer(final @NotNull Player player);

    @NotNull Component hookGetTitle();

    void hookSetTitle(final @NotNull ComponentLike title);

    @Nullable Component hookGetLine(final int index);

    void hookSetLine(final int index, final @NotNull ComponentLike line);

    void hookAddLine(final @NotNull ComponentLike line);

    void hookRemoveLine(final int index);

    void hookRemoveLines();

    void hookInsertLine(final int index, final @NotNull ComponentLike line);

    void hookClearLine(final int index);

    void hookClearLines();

    int hookSize();
}
