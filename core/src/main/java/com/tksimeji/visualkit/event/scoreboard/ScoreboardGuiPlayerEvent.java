package com.tksimeji.visualkit.event.scoreboard;

import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.event.EventImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

abstract class ScoreboardGuiPlayerEvent extends EventImpl implements Event {
    protected final @NotNull Player player;

    public ScoreboardGuiPlayerEvent(final @NotNull Object gui, final @NotNull Player player) {
        super(gui);
        this.player = player;
    }

    public final @NotNull Player getPlayer() {
        return player;
    }
}
