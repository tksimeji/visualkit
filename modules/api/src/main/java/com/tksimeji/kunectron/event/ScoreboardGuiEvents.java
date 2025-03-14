package com.tksimeji.kunectron.event;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiEvents {
    private ScoreboardGuiEvents() {
    }

    public interface InitEvent extends GuiEvent {
    }

    public interface PlayerAddEvent extends GuiEvent {
        @NotNull Player getPlayer();
    }

    public interface PlayerRemoveEvent extends GuiEvent {
        @NotNull Player getPlayer();
    }

    public interface TickEvent extends GuiEvent {
    }
}
