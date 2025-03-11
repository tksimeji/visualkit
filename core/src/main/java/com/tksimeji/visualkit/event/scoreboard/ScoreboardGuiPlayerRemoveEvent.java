package com.tksimeji.visualkit.event.scoreboard;

import com.tksimeji.visualkit.event.ScoreboardGuiEvents;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiPlayerRemoveEvent extends ScoreboardGuiPlayerEvent implements ScoreboardGuiEvents.PlayerRemoveEvent {
    public ScoreboardGuiPlayerRemoveEvent(final @NotNull Object gui, final @NotNull Player player) {
        super(gui, player);
    }
}
