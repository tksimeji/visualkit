package com.tksimeji.visualkit.event;

import org.jetbrains.annotations.NotNull;

public abstract class EventImpl implements Event {
    private final @NotNull Object gui;

    public EventImpl(final @NotNull Object gui) {
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
