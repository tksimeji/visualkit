package com.tksimeji.kunectron.hooks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@ApiStatus.Internal
interface IScoreboardGuiHooks extends Hooks {
    @NotNull Set<Player> usePlayers();

    void useAddPlayer(final @NotNull Player player);

    void useRemovePlayer(final @NotNull Player player);

    boolean useIsPlayer(final @NotNull Player player);

    @NotNull Component useTitle();

    void useTitle(final @NotNull ComponentLike title);

    @Nullable Component useLine(final int index);

    void useLine(final int index, final @NotNull ComponentLike line);

    void useAddLine(final @NotNull ComponentLike line);

    void useRemoveLine(final int index);

    void useRemoveLines();

    void useInsertLine(final int index, final @NotNull ComponentLike line);

    void useClearLine(final int index);

    void useClearLines();

    @NotNull List<Component> useLines();

    int useSize();

    void useClose();
}
