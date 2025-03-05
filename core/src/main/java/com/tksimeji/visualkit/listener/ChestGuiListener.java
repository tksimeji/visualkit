package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.ChestGuiController;
import com.tksimeji.visualkit.type.ChestGuiType;
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

public final class ChestGuiListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(final @NotNull InventoryClickEvent event) {
        ChestGuiController controller = getController(event.getInventory());

        if (controller == null) {
            return;
        }

        Action action = event.getClick() == ClickType.DOUBLE_CLICK ? Action.DOUBLE_CLICK : event.isShiftClick() ? Action.SHIFT_CLICK : Action.SINGLE_CLICK;
        Mouse mouse = event.getClick().isLeftClick() ? Mouse.LEFT : Mouse.RIGHT;
        event.setCancelled(controller.click(event.getSlot(), action, mouse));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(final @NotNull InventoryCloseEvent event) {
        ChestGuiController controller = getController(event.getInventory());

        if (controller == null) {
            return;
        }

        controller.close();
    }

    @ApiStatus.Internal
    private @Nullable ChestGuiController getController(final @Nullable Inventory inventory) {
        return Visualkit.getGuiControllers(ChestGuiType.instance())
                .stream()
                .filter(aController -> aController.getInventory() == inventory)
                .findFirst()
                .orElse(null);
    }
}
