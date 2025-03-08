package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.MerchantGuiController;
import com.tksimeji.visualkit.element.TradeElementImpl;
import com.tksimeji.visualkit.type.MerchantGuiType;
import io.papermc.paper.event.player.PlayerPurchaseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class MerchantGuiListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPurchase(final @NotNull PlayerPurchaseEvent event) {
        MerchantGuiController controller = controller(event.getPlayer());

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

        MerchantGuiController controller = controller(player);

        if (controller == null) {
            return;
        }

        event.setCancelled(controller.select(event.getIndex(), Objects.requireNonNull(controller.getElement(event.getIndex()))));
    }

    @ApiStatus.Internal
    private @Nullable MerchantGuiController controller(final @Nullable Player player) {
        return Visualkit.getGuiControllers(MerchantGuiType.instance())
                .stream()
                .filter(controller -> controller.getPlayer() == player)
                .findFirst()
                .orElse(null);
    }
}
