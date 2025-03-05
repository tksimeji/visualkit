package com.tksimeji.visualkit.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import com.tksimeji.visualkit.Tickable;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.GuiController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class ServerListener implements Listener {
    @EventHandler
    public void onServerTickStart(final @NotNull ServerTickStartEvent event) {
        for (GuiController controller : Visualkit.getGuiControllers()) {
            if (!(controller instanceof Tickable tickable)) {
                continue;
            }

            tickable.tick();
        }
    }
}
