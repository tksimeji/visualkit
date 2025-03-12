package com.tksimeji.kunectron.event.anvil;

import com.tksimeji.kunectron.event.AnvilGuiEvents;
import com.tksimeji.kunectron.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public class AnvilGuiInitEventImpl extends EventImpl implements AnvilGuiEvents.InitEvent {
    public AnvilGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
