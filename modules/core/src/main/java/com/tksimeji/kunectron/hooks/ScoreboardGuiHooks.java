package com.tksimeji.kunectron.hooks;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.GuiController;
import com.tksimeji.kunectron.controller.ScoreboardGuiController;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ScoreboardGuiHooks extends IScoreboardGuiHooks {
    @Override
    default @NotNull Set<Player> hookGetPlayers() {
        return controller().getPlayers();
    }

    @Override
    default void hookAddPlayer(final @NotNull Player player) {
        Preconditions.checkArgument(player != null, "Player cannot be null.");
        controller().addPlayer(player);
    }

    @Override
    default void hookRemovePlayer(final @NotNull Player player) {
        Preconditions.checkArgument(player != null, "Player cannot be null.");
        controller().removePlayer(player);
    }

    @Override
    default boolean hookIsPlayer(final @NotNull Player player) {
        return controller().isPlayer(player);
    }

    @Override
    default @NotNull Component hookGetTitle() {
        return controller().getTitle();
    }

    @Override
    default void hookSetTitle(final @NotNull ComponentLike title) {
        Preconditions.checkArgument(title != null, "Title cannot be null.");
        controller().setTitle(title);
    }

    @Override
    default @Nullable Component hookGetLine(final int index) {
        return controller().getLine(index);
    }

    @Override
    default void hookSetLine(final int index, final @NotNull ComponentLike line) {
        Preconditions.checkArgument(line != null, "Line cannot be null.");
        controller().setLine(index, line);
    }

    @Override
    default void hookAddLine(final @NotNull ComponentLike line) {
        Preconditions.checkArgument(line != null, "Line cannot be null.");
        controller().addLine(line);
    }

    @Override
    default void hookRemoveLine(final int index) {
        controller().removeLine(index);
    }

    @Override
    default void hookRemoveLines() {
        controller().removeLines();
    }

    @Override
    default void hookInsertLine(final int index, final @NotNull ComponentLike line) {
        Preconditions.checkArgument(line != null, "Line cannot be null.");
        controller().insertLine(index, line);
    }

    @Override
    default void hookClearLine(final int index) {
        controller().clearLine(index);
    }

    @Override
    default void hookClearLines() {
        controller().clearLines();
    }

    @Override
    default int hookSize() {
        return controller().getSize();
    }

    @Override
    default void hookState(final @NotNull String key, final @Nullable Object value) {
        Preconditions.checkArgument(key != null, "Key cannot be null.");
        controller().setState(key, value);
    }

    private @NotNull ScoreboardGuiController controller() {
        GuiController controller = Kunectron.getGuiController(this);

        if (!(controller instanceof ScoreboardGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (ScoreboardGuiController) controller;
    }
}
