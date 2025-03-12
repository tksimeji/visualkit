package com.tksimeji.kunectron.listener;

import com.tksimeji.kunectron.controller.ScoreboardGuiController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardGuiListener implements Listener {
    @EventHandler
    public void onPlayerQuit(final @NotNull PlayerQuitEvent event) {
        ScoreboardGuiController controller = ScoreboardGuiController.get(event.getPlayer());

        if (controller == null) {
            return;
        }

        controller.removePlayer(event.getPlayer());
    }
}
