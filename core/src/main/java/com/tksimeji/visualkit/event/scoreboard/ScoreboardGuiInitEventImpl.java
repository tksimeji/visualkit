package com.tksimeji.visualkit.event.scoreboard;

import com.tksimeji.visualkit.event.EventImpl;
import com.tksimeji.visualkit.event.ScoreboardGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiInitEventImpl extends EventImpl implements ScoreboardGuiEvents.InitEvent {
    public ScoreboardGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
