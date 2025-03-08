package com.tksimeji.visualkit.event;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class VisualkitEvent extends org.bukkit.event.Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @NotNull Event event;

    public VisualkitEvent(final @NotNull Event event) {
        this.event = event;
    }

    public @NotNull Event getEvent() {
        return event;
    }

    public @NotNull Object getSource() {
        return event.getGui();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
