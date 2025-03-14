package com.tksimeji.kunectron.event;

public interface CancellableEvent extends GuiEvent {
    boolean isCancelled();

    void setCancelled(final boolean cancelled);
}
