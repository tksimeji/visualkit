package com.tksimeji.visualkit.hooks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

interface IScoreboardGuiHooks extends Hooks {
    @NotNull Set<Player> useGetPlayers();

    void useAddPlayer(final @NotNull Player player);

    void useRemovePlayer(final @NotNull Player player);

    boolean useIsPlayer(final @NotNull Player player);

    @NotNull Component useGetTitle();

    void useSetTitle(final @NotNull ComponentLike title);

    @Nullable Component useGetLine(final int index);

    void useSetLine(final int index, final @NotNull ComponentLike line);

    void useAddLine(final @NotNull ComponentLike line);

    void useRemoveLine(final int index);

    void useRemoveLines();

    void useInsertLine(final int index, final @NotNull ComponentLike line);

    void useClearLine(final int index);

    void useClearLines();

    int useSize();
}
