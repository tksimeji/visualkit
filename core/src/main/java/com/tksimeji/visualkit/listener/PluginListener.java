package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.lang.Languages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class PluginListener implements Listener {
    @EventHandler
    public void onPluginEnable(@NotNull PluginEnableEvent event) {
        Plugin plugin = event.getPlugin();

        if (plugin.getPluginMeta().getPluginDependencies().stream().noneMatch(d -> d.equals(Visualkit.plugin().getName()))) {
            return;
        }

        Languages.mount(plugin);
    }

    @EventHandler
    public void onPluginDisable(@NotNull PluginDisableEvent event) {
        Languages.unmount(event.getPlugin());
    }
}
