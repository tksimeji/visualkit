package com.tksimeji.visualkit.hooks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public interface ScoreboardGuiHooks extends Hooks {
    default @NotNull Component useGetTitle() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useSetTitle(@NotNull ComponentLike title) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default @NotNull Component useGetLine(int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useSetLine(int index, @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useAddLine(@NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useRemoveLine(int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useRemoveLines() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useInsertLine(int index, @NotNull ComponentLike line) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useClearLine(int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useClearLines() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default int useSize() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
