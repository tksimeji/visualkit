package com.tksimeji.visualkit.event.chest;

import com.tksimeji.visualkit.event.ChestGuiEvents;
import com.tksimeji.visualkit.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public final class ChestGuiCloseEventImpl extends EventImpl implements ChestGuiEvents.CloseEvent {
    public ChestGuiCloseEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
