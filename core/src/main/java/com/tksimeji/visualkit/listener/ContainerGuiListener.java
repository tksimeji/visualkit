package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.ContainerGuiController;
import com.tksimeji.visualkit.controller.ItemContainerGuiController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContainerGuiListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(final @NotNull InventoryClickEvent event) {
        ContainerGuiController<?> controller = getController(event.getInventory());

        if (!(controller instanceof ItemContainerGuiController<?> itemContainerGuiController)) {
            return;
        }

        Action action = event.getClick() == ClickType.DOUBLE_CLICK ? Action.DOUBLE_CLICK : event.isShiftClick() ? Action.SHIFT_CLICK : Action.SINGLE_CLICK;
        Mouse mouse = event.getClick().isLeftClick() ? Mouse.LEFT : Mouse.RIGHT;
        event.setCancelled(itemContainerGuiController.click(event.getSlot(), action, mouse));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(final @NotNull InventoryCloseEvent event) {
        ContainerGuiController<?> controller = getController(event.getInventory());

        if (controller != null) {
            controller.close();
        }
    }

    @ApiStatus.Internal
    private @Nullable ContainerGuiController<?> getController(final @Nullable Inventory inventory) {
        return Visualkit.getGuiControllers()
                .stream()
                .filter(controller -> (controller instanceof ContainerGuiController<?> containerGuiController) && containerGuiController.getInventory() == inventory)
                .map(controller -> (ContainerGuiController<?>) controller)
                .findFirst()
                .orElse(null);
    }
}
