package com.tksimeji.kunectron.event.chest;

import com.tksimeji.kunectron.event.ChestGuiEvents;
import com.tksimeji.kunectron.event.GuiEventImpl;
import org.jetbrains.annotations.NotNull;

public final class ChestGuiCloseEventImpl extends GuiEventImpl implements ChestGuiEvents.CloseEvent {
    public ChestGuiCloseEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
