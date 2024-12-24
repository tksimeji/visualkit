package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.IInventoryUI;
import com.tksimeji.visualkit.InventoryUI;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.api.Click;
import com.tksimeji.visualkit.api.Mouse;
import com.tksimeji.visualkit.policy.PolicyTarget;
import com.tksimeji.visualkit.policy.SlotPolicy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class InventoryListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (! (event.getWhoClicked() instanceof Player player)) {
            return;
        }

        IInventoryUI<?> ui = Visualkit.getInventoryUI(player);

        if (ui == null) {
            return;
        }

        PolicyTarget target = event.getClickedInventory() == ui.asInventory() ? PolicyTarget.UI : PolicyTarget.INVENTORY;

        int slot = event.getSlot();

        if (0 <= slot) {
            event.setCancelled(ui.getPolicy(slot, target) == SlotPolicy.FIXATION || event.isCancelled());
        } else {
            slot = -1;
            event.setCancelled((ui.getPolicy(slot, PolicyTarget.UI) == SlotPolicy.FIXATION && ui.getPolicy(slot, PolicyTarget.INVENTORY) == SlotPolicy.FIXATION) ||
                    event.isCancelled());
        }

        ItemStack currentItem = event.getCurrentItem();

        if (! event.isCancelled() && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && currentItem != null) {
            event.setCancelled(true);

            Inventory inventory = target == PolicyTarget.UI ? player.getOpenInventory().getBottomInventory() : ui.asInventory();

            int remainingAmount = currentItem.getAmount();

            for (int i = 0; i < inventory.getSize(); i ++) {
                if (ui.getPolicy(i, target == PolicyTarget.UI ? PolicyTarget.INVENTORY : PolicyTarget.UI) == SlotPolicy.FIXATION) {
                    continue;
                }

                ItemStack itemStack = inventory.getItem(i);
                Material type = currentItem.getType();

                if (itemStack != null && (itemStack.getType() != type || itemStack.getMaxStackSize() <= itemStack.getAmount())) {
                    continue;
                }

                int amount = Math.min(remainingAmount, itemStack != null ? type.getMaxStackSize() - itemStack.getAmount() : type.getMaxStackSize());

                remainingAmount -= amount;

                if (itemStack == null) {
                    itemStack = new ItemStack(type);
                } else {
                    amount += itemStack.getAmount();
                }

                itemStack.setAmount(amount);
                inventory.setItem(i, itemStack);

                if (remainingAmount <= 0) {
                    break;
                }
            }

            currentItem.setAmount(remainingAmount);

            event.setCurrentItem(currentItem);
        }

        if (target != PolicyTarget.UI) {
            return;
        }

        Click click = event.getClick() == ClickType.DOUBLE_CLICK ? Click.DOUBLE : event.isShiftClick() ? Click.SHIFT : Click.SINGLE;
        Mouse mouse = event.getClick().isLeftClick() ? Mouse.LEFT : Mouse.RIGHT;

        ui.onClick(event.getSlot(), click, mouse);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(@NotNull InventoryDragEvent event) {
        if (! (event.getWhoClicked() instanceof Player player)) {
            return;
        }

        IInventoryUI<?> ui = Visualkit.getInventoryUI(player);

        if (ui == null) {
            return;
        }

        event.setCancelled(event.getRawSlots().stream().anyMatch(raw -> {
            PolicyTarget target = raw < ui.getSize() ? PolicyTarget.UI : PolicyTarget.INVENTORY;
            int slot = player.getOpenInventory().convertSlot(raw);
            return ui.getPolicy(slot, target) != SlotPolicy.VARIATION;
        }) || event.isCancelled());

        if (event.isCancelled() || event.getInventory() != ui.asInventory()) {
            return;
        }

        event.getInventorySlots().forEach(slot -> ui.onClick(slot, Click.DRAG, Mouse.RIGHT));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(@NotNull InventoryCloseEvent event) {
        Visualkit.sessions(InventoryUI.class).stream()
                .filter(session -> session.asInventory() ==  event.getView().getTopInventory())
                .forEach(InventoryUI::close);
    }
}
