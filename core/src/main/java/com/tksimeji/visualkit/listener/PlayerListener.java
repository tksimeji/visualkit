package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.*;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Optional.ofNullable(Visualkit.getSessions(IPanelUI.class, player)).ifPresent(panel -> {
            if (panel instanceof PanelUI p) {
                p.kill();
            } else if (panel instanceof SharedPanelUI s) {
                s.removeAudience(player);
            }
        });
    }

    @EventHandler
    public void on(AsyncChatEvent event) {
        new AnvilUI(event.getPlayer()) {
            @NotNull
            @Override
            public Component title() {
                return Component.text("Search");
            }

            @Override
            public void onTyped(@NotNull String string) {
                System.out.println(string);
            }
        };
    }
}
