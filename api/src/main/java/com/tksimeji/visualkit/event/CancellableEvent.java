package com.tksimeji.visualkit.event;

public interface CancellableEvent extends Event {
    boolean isCancelled();

    void setCancelled(final boolean cancelled);
}
