package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.event.GuiEvent;
import org.jetbrains.annotations.NotNull;

public interface GuiController {
    default void init() {
    }

    @NotNull Object getGui();

    boolean callEvent(final @NotNull GuiEvent event);
}
