package com.tksimeji.kunectron.event;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class KunectronEvent extends org.bukkit.event.Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final @NotNull GuiEvent event;

    public KunectronEvent(final @NotNull GuiEvent event) {
        this.event = event;
    }

    public @NotNull GuiEvent getEvent() {
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
