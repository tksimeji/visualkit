package com.tksimeji.visualkit.event.anvil;

import com.tksimeji.visualkit.event.AnvilGuiEvents;
import com.tksimeji.visualkit.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public class AnvilGuiInitEventImpl extends EventImpl implements AnvilGuiEvents.InitEvent {
    public AnvilGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
