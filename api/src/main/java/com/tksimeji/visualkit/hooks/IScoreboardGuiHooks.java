package com.tksimeji.visualkit.hooks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface IScoreboardGuiHooks extends Hooks {
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
