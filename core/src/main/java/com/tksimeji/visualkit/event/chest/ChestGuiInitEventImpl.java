package com.tksimeji.visualkit.event.chest;

import com.tksimeji.visualkit.event.ChestGuiEvents;
import com.tksimeji.visualkit.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public final class ChestGuiInitEventImpl extends EventImpl implements ChestGuiEvents.InitEvent {
    public ChestGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
