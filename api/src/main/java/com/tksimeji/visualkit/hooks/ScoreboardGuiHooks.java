package com.tksimeji.visualkit.hooks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ScoreboardGuiHooks extends IScoreboardGuiHooks {
    @Override
    default @NotNull Set<Player> useGetPlayers() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useAddPlayer(final @NotNull Player player) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useRemovePlayer(final @NotNull Player player) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default boolean useIsPlayer(final @NotNull Player player) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull Component useGetTitle() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetTitle(final @NotNull ComponentLike title) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull Component useGetLine(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetLine(final int index, final @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useAddLine(final @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useRemoveLine(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useRemoveLines() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useInsertLine(final int index, final @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useClearLine(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useClearLines() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default int useSize() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
