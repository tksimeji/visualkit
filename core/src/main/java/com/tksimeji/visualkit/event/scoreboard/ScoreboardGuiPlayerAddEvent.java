package com.tksimeji.visualkit.event.scoreboard;

import com.tksimeji.visualkit.event.ScoreboardGuiEvents;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiPlayerAddEvent extends ScoreboardGuiPlayerEvent implements ScoreboardGuiEvents.PlayerAddEvent {
    public ScoreboardGuiPlayerAddEvent(final @NotNull Object gui, final @NotNull Player player) {
        super(gui, player);
    }
}
