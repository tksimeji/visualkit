package com.tksimeji.kunectron.event;

public final class ChestGuiEvents {
    private ChestGuiEvents() {
        throw new UnsupportedOperationException();
    }

    public interface ClickEvent extends ItemContainerClickEvent {
    }

    public interface CloseEvent extends Event {
    }

    public interface InitEvent extends Event {
    }
}
