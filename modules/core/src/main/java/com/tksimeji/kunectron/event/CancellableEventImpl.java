package com.tksimeji.kunectron.event;

import org.jetbrains.annotations.NotNull;

public class CancellableEventImpl extends GuiEventImpl implements CancellableEvent  {
    private boolean cancelled;

    public CancellableEventImpl(final @NotNull Object gui) {
        this(gui, false);
    }

    public CancellableEventImpl(final @NotNull Object gui, boolean cancelled) {
        super(gui);
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
