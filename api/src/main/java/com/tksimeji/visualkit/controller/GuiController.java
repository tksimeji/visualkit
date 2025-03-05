package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.event.VisualkitEvent;
import org.jetbrains.annotations.NotNull;

public interface GuiController {
    @NotNull Object getGui();

    boolean callEvent(final @NotNull VisualkitEvent event);
}
