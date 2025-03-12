package com.tksimeji.kunectron.event.chest;

import com.tksimeji.kunectron.event.ChestGuiEvents;
import com.tksimeji.kunectron.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public final class ChestGuiCloseEventImpl extends EventImpl implements ChestGuiEvents.CloseEvent {
    public ChestGuiCloseEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
