package com.tksimeji.kunectron.event.anvil;

import com.tksimeji.kunectron.event.AnvilGuiEvents;
import com.tksimeji.kunectron.event.GuiEventImpl;
import org.jetbrains.annotations.NotNull;

public final class AnvilGuiCloseEventImpl extends GuiEventImpl implements AnvilGuiEvents.CloseEvent {
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
