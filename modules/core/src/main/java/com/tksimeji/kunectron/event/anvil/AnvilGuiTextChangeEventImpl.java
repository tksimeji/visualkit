package com.tksimeji.kunectron.event.anvil;

import com.tksimeji.kunectron.event.AnvilGuiEvents;
import com.tksimeji.kunectron.event.GuiEventImpl;
import org.jetbrains.annotations.NotNull;

public final class AnvilGuiTextChangeEventImpl extends GuiEventImpl implements AnvilGuiEvents.TextChangeEvent {
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
