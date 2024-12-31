package com.tksimeji.visualkit.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.IVisualkitUI;
import com.tksimeji.visualkit.xmpl.Xmpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class ServerListener implements Listener {
    @EventHandler
    public void onServerTickStart(@NotNull ServerTickStartEvent event) {
        Xmpl.getInstances().forEach(Xmpl::tick);
        Visualkit.getSessions().forEach(IVisualkitUI::tick);
    }
}
