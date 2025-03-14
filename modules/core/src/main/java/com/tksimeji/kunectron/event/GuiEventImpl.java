package com.tksimeji.kunectron.event;

import org.jetbrains.annotations.NotNull;

public abstract class GuiEventImpl implements GuiEvent {
    private final @NotNull Object gui;

    public GuiEventImpl(final @NotNull Object gui) {
        this.gui = gui;
    }

    @Override
    public @NotNull String getEventName() {
        return getClass().getSimpleName();
    }

    @Override
    public @NotNull Object getGui() {
        return gui;
    }
}
