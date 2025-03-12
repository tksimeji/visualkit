package com.tksimeji.kunectron.event;

public interface CancellableEvent extends Event {
    boolean isCancelled();

    void setCancelled(final boolean cancelled);
}
