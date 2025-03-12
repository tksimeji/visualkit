package com.tksimeji.kunectron.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import com.tksimeji.kunectron.controller.TickableGuiController;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.GuiController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class ServerListener implements Listener {
    @EventHandler
    public void onServerTickStart(final @NotNull ServerTickStartEvent event) {
        for (GuiController controller : Kunectron.getGuiControllers()) {
            if (!(controller instanceof TickableGuiController tickable)) {
                continue;
            }

            tickable.tick();
        }
    }
}
