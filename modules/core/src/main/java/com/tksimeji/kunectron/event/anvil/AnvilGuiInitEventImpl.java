package com.tksimeji.kunectron.event.anvil;

import com.tksimeji.kunectron.event.AnvilGuiEvents;
import com.tksimeji.kunectron.event.GuiEventImpl;
import org.jetbrains.annotations.NotNull;

public class AnvilGuiInitEventImpl extends GuiEventImpl implements AnvilGuiEvents.InitEvent {
    public AnvilGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
