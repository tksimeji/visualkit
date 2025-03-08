package com.tksimeji.visualkit.event.anvil;

import com.tksimeji.visualkit.event.AnvilGuiEvents;
import com.tksimeji.visualkit.event.EventImpl;
import org.jetbrains.annotations.NotNull;

public final class AnvilGuiTextChangeEventImpl extends EventImpl implements AnvilGuiEvents.TextChangeEvent {
    private final @NotNull String text;

    public AnvilGuiTextChangeEventImpl(final @NotNull Object gui, final @NotNull String text) {
        super(gui);
        this.text = text;
    }

    @Override
    public @NotNull String getText() {
        return text;
    }
}
