package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.controller.MerchantGuiController;
import com.tksimeji.visualkit.element.TradeElementImpl;
import io.papermc.paper.event.player.PlayerPurchaseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class MerchantGuiListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPurchase(final @NotNull PlayerPurchaseEvent event) {
        MerchantGuiController controller = MerchantGuiController.get(event.getPlayer());

        if (controller == null) {
            return;
        }

        controller.getElements().stream()
                .filter(aElement -> ((TradeElementImpl) aElement).equals(event.getTrade()))
                .findFirst()
                .ifPresent(element -> event.setCancelled(controller.purchase(controller.getElements().indexOf(element), element)));

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

        event.setCancelled(controller.select(event.getIndex(), Objects.requireNonNull(controller.getElement(event.getIndex()))));
    }
}
