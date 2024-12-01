package com.tksimeji.visualkit.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import com.tksimeji.visualkit.IInventoryUI;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.VisualkitUI;
import com.tksimeji.visualkit.element.Xmpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class ServerListener implements Listener {
    @EventHandler
    public void onServerTickStart(@NotNull ServerTickStartEvent event) {
        Xmpl.getInstances().forEach(Xmpl::tick);
        Visualkit.sessions().forEach(VisualkitUI::tick);
    }
}
