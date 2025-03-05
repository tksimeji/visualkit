package com.tksimeji.visualkit.event;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

abstract class AbstractGuiEvent extends Event implements GuiEvent {
    private final @NotNull Object gui;

    public AbstractGuiEvent(final @NotNull Object gui) {
        this.gui = gui;
    }

    @Override
    public @NotNull Object getGui() {
        return gui;
    }
}
