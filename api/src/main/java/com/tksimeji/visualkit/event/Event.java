package com.tksimeji.visualkit.event;

import org.jetbrains.annotations.NotNull;

public interface Event {
    @NotNull String getEventName();

    @NotNull Object getGui();
}
