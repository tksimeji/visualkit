package com.tksimeji.visualkit.event.anvil;

import com.tksimeji.visualkit.event.AnvilGuiEvents;
import com.tksimeji.visualkit.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public final class AnvilGuiCloseEventImpl extends EventImpl implements AnvilGuiEvents.CloseEvent {
    private final @NotNull String text;

    public AnvilGuiCloseEventImpl(final @NotNull Object gui, final @NotNull String text) {
        super(gui);
        this.text = text;
    }

    @Override
    public @NotNull String getText() {
        return text;
    }
}
