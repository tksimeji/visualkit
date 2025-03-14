package com.tksimeji.kunectron.event.scoreboard;

import com.tksimeji.kunectron.event.GuiEvent;
import com.tksimeji.kunectron.event.GuiEventImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

abstract class ScoreboardGuiPlayerEvent extends GuiEventImpl implements GuiEvent {
    protected final @NotNull Player player;

    public ScoreboardGuiPlayerEvent(final @NotNull Object gui, final @NotNull Player player) {
        super(gui);
        this.player = player;
    }

    public final @NotNull Player getPlayer() {
        return player;
    }
}
