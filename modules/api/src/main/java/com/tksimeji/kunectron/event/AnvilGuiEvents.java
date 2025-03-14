package com.tksimeji.kunectron.event;

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

    public interface CloseEvent extends GuiEvent {
        @NotNull String getText();
    }

    public interface InitEvent extends GuiEvent {
    }

    public interface TextChangeEvent extends GuiEvent {
        @NotNull String getText();
    }
}
