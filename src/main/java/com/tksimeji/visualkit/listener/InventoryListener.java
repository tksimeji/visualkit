package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.IInventoryUI;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.api.Click;
import com.tksimeji.visualkit.api.Mouse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

public final class InventoryListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (! (event.getWhoClicked() instanceof Player player)) {
            return;
        }

        IInventoryUI<?> ui = Visualkit.getInventoryUI(player);

        event.setCancelled(ui != null || event.isCancelled());

        if (ui == null || ui.asInventory() != event.getClickedInventory()) {
            return;
        }

        Click click = event.getClick() == ClickType.DOUBLE_CLICK ? Click.DOUBLE : event.isShiftClick() ? Click.SHIFT : Click.SINGLE;
        Mouse mouse = event.getClick().isLeftClick() ? Mouse.LEFT : Mouse.RIGHT;

        ui.onClick(event.getSlot(), click, mouse);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(@NotNull InventoryCloseEvent event) {
        if (! (event.getPlayer() instanceof Player player)) {
            return;
        }

        IInventoryUI<?> ui = Visualkit.getInventoryUI(player);

        if (ui == null) {
            return;
        }

        ui.close();
    }
}
