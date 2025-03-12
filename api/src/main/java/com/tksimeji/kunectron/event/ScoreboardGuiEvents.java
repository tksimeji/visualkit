package com.tksimeji.kunectron.event;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiEvents {
    private ScoreboardGuiEvents() {
    }

    public interface InitEvent extends Event {
    }

    public interface PlayerAddEvent extends Event {
        @NotNull Player getPlayer();
    }

    public interface PlayerRemoveEvent extends Event {
        @NotNull Player getPlayer();
    }

    public interface TickEvent extends Event {
    }
}
