package com.tksimeji.visualkit.event.scoreboard;

import com.tksimeji.visualkit.event.EventImpl;
import com.tksimeji.visualkit.event.ScoreboardGuiEvents;
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
