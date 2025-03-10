package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GuiController {
    default void init() {
    }

    @NotNull Object getGui();

    void setState(final @NotNull String name, final @Nullable Object value);

    boolean callEvent(final @NotNull Event event);
}
