package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.event.Event;
import org.jetbrains.annotations.NotNull;

public interface GuiController {
    default void init() {
    }

    @NotNull Object getGui();

    boolean callEvent(final @NotNull Event event);
}
