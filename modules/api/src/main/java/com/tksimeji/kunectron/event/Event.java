package com.tksimeji.kunectron.event;

import org.jetbrains.annotations.NotNull;

public interface Event {
    @NotNull String getEventName();

    @NotNull Object getGui();
}
