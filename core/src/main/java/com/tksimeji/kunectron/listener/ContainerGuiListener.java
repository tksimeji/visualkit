package com.tksimeji.kunectron.listener;

import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.ContainerGuiController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContainerGuiListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(final @NotNull InventoryCloseEvent event) {
        ContainerGuiController<?> controller = controller(event.getInventory());

        if (controller != null) {
            controller.close();
        }
    }

    @ApiStatus.Internal
    private @Nullable ContainerGuiController<?> controller(final @Nullable Inventory inventory) {
        return Kunectron.getGuiControllers()
                .stream()
                .filter(controller -> (controller instanceof ContainerGuiController<?> containerGuiController) && containerGuiController.getInventory() == inventory)
                .map(controller -> (ContainerGuiController<?>) controller)
                .findFirst()
                .orElse(null);
    }
}
