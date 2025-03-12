package com.tksimeji.kunectron.event.scoreboard;

import com.tksimeji.kunectron.event.EventImpl;
import com.tksimeji.kunectron.event.ScoreboardGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiTickEventImpl extends EventImpl implements ScoreboardGuiEvents.TickEvent {
    public ScoreboardGuiTickEventImpl(final @NotNull Object gui) {
        super(gui);
    }

    @Override
    public @NotNull Object getGui() {
        return null;
    }
}
