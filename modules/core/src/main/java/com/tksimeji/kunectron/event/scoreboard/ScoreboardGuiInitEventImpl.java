package com.tksimeji.kunectron.event.scoreboard;

import com.tksimeji.kunectron.event.EventImpl;
import com.tksimeji.kunectron.event.ScoreboardGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiInitEventImpl extends EventImpl implements ScoreboardGuiEvents.InitEvent {
    public ScoreboardGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
