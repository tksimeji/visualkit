package com.tksimeji.kunectron.controller;

import com.tksimeji.kunectron.event.GuiEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GuiController {
    default void init() {
    }

    @NotNull Object getGui();

    void setState(final @NotNull String name, final @Nullable Object value);

    boolean callEvent(final @NotNull GuiEvent event);
}
