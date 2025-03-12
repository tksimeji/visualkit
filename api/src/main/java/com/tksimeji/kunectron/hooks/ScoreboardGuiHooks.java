package com.tksimeji.kunectron.hooks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ScoreboardGuiHooks extends IScoreboardGuiHooks {
    @Override
    default @NotNull Set<Player> hookGetPlayers() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookAddPlayer(final @NotNull Player player) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookRemovePlayer(final @NotNull Player player) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default boolean hookIsPlayer(final @NotNull Player player) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull Component hookGetTitle() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetTitle(final @NotNull ComponentLike title) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @NotNull Component hookGetLine(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookSetLine(final int index, final @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookAddLine(final @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookRemoveLine(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookRemoveLines() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookInsertLine(final int index, final @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookClearLine(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void hookClearLines() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default int hookSize() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
