package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.*;
import com.tksimeji.visualkit.trade.VisualkitTrade;
import io.papermc.paper.event.player.PlayerPurchaseEvent;
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

        Optional.ofNullable(Visualkit.getSession(IPanelUI.class, player)).ifPresent(panel -> {
            if (panel instanceof PanelUI p) {
                p.kill();
            } else if (panel instanceof SharedPanelUI s) {
                s.removeAudience(player);
            }
        });
    }

    @EventHandler
    public void onPlayerPurchase(@NotNull PlayerPurchaseEvent event) {
        IMerchantUI ui = Visualkit.getSession(IMerchantUI.class, event.getPlayer());

        if (ui == null) {
            return;
        }

        VisualkitTrade trade = ui.getTrades().stream().filter(p -> p.equals(event.getTrade())).findFirst().orElse(null);

        if (trade == null) {
            return;
        }

        event.setCancelled(! ui.onPurchase(trade) || ! trade.purchasable() || event.isCancelled());
    }
}
