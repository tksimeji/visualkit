package com.tksimeji.kunectron.event;

import org.jetbrains.annotations.NotNull;

public interface GuiEvent {
    @NotNull String getEventName();

    @NotNull Object getGui();
}
