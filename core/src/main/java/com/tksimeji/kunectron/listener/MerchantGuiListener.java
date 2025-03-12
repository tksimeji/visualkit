package com.tksimeji.kunectron.listener;

import com.tksimeji.kunectron.controller.MerchantGuiController;
import io.papermc.paper.event.player.PlayerPurchaseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPurchase(final @NotNull PlayerPurchaseEvent event) {
        MerchantGuiController controller = MerchantGuiController.get(event.getPlayer());

        if (controller == null) {
            return;
        }

        int index = controller.getInventory().getMerchant().getRecipes().indexOf(event.getTrade());
        event.setCancelled(controller.purchase(index));

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTradeSelect(final @NotNull TradeSelectEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        MerchantGuiController controller = MerchantGuiController.get(player);

        if (controller == null) {
            return;
        }

        event.setCancelled(controller.select(event.getIndex()));
    }
}
