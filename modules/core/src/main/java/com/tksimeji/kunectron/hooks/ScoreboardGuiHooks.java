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

import java.util.List;
import java.util.Set;

public interface ScoreboardGuiHooks extends IScoreboardGuiHooks {
    @Override
    default @NotNull Set<Player> usePlayers() {
        return controller().getPlayers();
    }

    @Override
    default void useAddPlayer(final @NotNull Player player) {
        Preconditions.checkArgument(player != null, "Player cannot be null.");
        controller().addPlayer(player);
    }

    @Override
    default void useRemovePlayer(final @NotNull Player player) {
        Preconditions.checkArgument(player != null, "Player cannot be null.");
        controller().removePlayer(player);
    }

    @Override
    default boolean useIsPlayer(final @NotNull Player player) {
        return controller().isPlayer(player);
    }

    @Override
    default @NotNull Component useTitle() {
        return controller().getTitle();
    }

    @Override
    default void useTitle(final @NotNull ComponentLike title) {
        Preconditions.checkArgument(title != null, "Title cannot be null.");
        controller().setTitle(title);
    }

    @Override
    default @Nullable Component useLine(final int index) {
        return controller().getLine(index);
    }

    @Override
    default void useLine(final int index, final @NotNull ComponentLike line) {
        Preconditions.checkArgument(line != null, "Line cannot be null.");
        controller().setLine(index, line);
    }

    @Override
    default void useAddLine(final @NotNull ComponentLike line) {
        Preconditions.checkArgument(line != null, "Line cannot be null.");
        controller().addLine(line);
    }

    @Override
    default void useRemoveLine(final int index) {
        controller().removeLine(index);
    }

    @Override
    default void useRemoveLines() {
        controller().removeLines();
    }

    @Override
    default void useInsertLine(final int index, final @NotNull ComponentLike line) {
        Preconditions.checkArgument(line != null, "Line cannot be null.");
        controller().insertLine(index, line);
    }

    @Override
    default void useClearLine(final int index) {
        controller().clearLine(index);
    }

    @Override
    default void useClearLines() {
        controller().clearLines();
    }

    @Override
    default @NotNull List<Component> useLines() {
        return controller().getLines();
    }

    @Override
    default int useSize() {
        return controller().getSize();
    }

    @Override
    default void useClose() {
        controller().close();
    }

    @Override
    default void useState(final @NotNull String key, final @Nullable Object value) {
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
