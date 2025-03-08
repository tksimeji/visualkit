package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.controller.ScoreboardGuiController;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ScoreboardGuiHooks extends IScoreboardGuiHooks {
    @Override
    default @NotNull Component useGetTitle() {
        return controller().getTitle();
    }

    @Override
    default void useSetTitle(final @NotNull ComponentLike title) {
        controller().setTitle(title);
    }

    @Override
    default @Nullable Component useGetLine(final int index) {
        return controller().getLine(index);
    }

    @Override
    default void useSetLine(final int index, final @NotNull ComponentLike line) {
        controller().setLine(index, line);
    }

    @Override
    default void useAddLine(final @NotNull ComponentLike line) {
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
    default int useSize() {
        return controller().getSize();
    }

    private @NotNull ScoreboardGuiController controller() {
        GuiController controller = Visualkit.getGuiController(this);

        if (!(controller instanceof ScoreboardGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (ScoreboardGuiController) controller;
    }
}
