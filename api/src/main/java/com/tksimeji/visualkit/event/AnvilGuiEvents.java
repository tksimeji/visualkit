package com.tksimeji.visualkit.event;

import org.jetbrains.annotations.NotNull;

public final class AnvilGuiEvents {
    private AnvilGuiEvents() {
        throw new UnsupportedOperationException();
    }

    public interface ClickEvent extends ItemContainerClickEvent {
        boolean isFirstSlot();

        boolean isSecondSlot();

        boolean isResultSlot();
    }

    public interface CloseEvent extends Event {
        @NotNull String getText();
    }

    public interface InitEvent extends Event {
    }

    public interface TextChangeEvent extends Event {
        @NotNull String getText();
    }
}
