package com.tksimeji.kunectron.event.chest;

import com.tksimeji.kunectron.event.ChestGuiEvents;
import com.tksimeji.kunectron.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public final class ChestGuiInitEventImpl extends EventImpl implements ChestGuiEvents.InitEvent {
    public ChestGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
