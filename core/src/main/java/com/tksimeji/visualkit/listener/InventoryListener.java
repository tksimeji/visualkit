package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.ChestUI;
import com.tksimeji.visualkit.ContainerUI;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.api.Click;
import com.tksimeji.visualkit.api.Mouse;
import com.tksimeji.visualkit.policy.PolicyTarget;
import com.tksimeji.visualkit.policy.SlotPolicy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class InventoryListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onContainerClick(@NotNull InventoryClickEvent event) {
        if (! (event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ContainerUI<?> ui = Visualkit.getSessions(ContainerUI.class, player);

        if (ui == null || ui instanceof ChestUI || event.getClickedInventory() != ui.asInventory()) {
            return;
        }

        ItemStack item = Optional.ofNullable(event.getCurrentItem()).orElseGet(() -> event.getCursor().getType().isAir() ? null : event.getCursor());
        Mouse mouse = event.getClick().isLeftClick() ? Mouse.LEFT : Mouse.RIGHT;
        Click click = event.getClick() == ClickType.DOUBLE_CLICK ? Click.DOUBLE : event.isShiftClick() ? Click.SHIFT : Click.SINGLE;

        event.setCancelled(! ui.onClick(event.getSlot(), click, mouse, item));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChestClick(@NotNull InventoryClickEvent event) {
        if (! (event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ChestUI ui = Visualkit.getSessions(ChestUI.class, player);

        if (ui == null) {
            return;
        }

        int slot = Math.max(event.getSlot(), -1);
        Mouse mouse = event.getClick().isLeftClick() ? Mouse.LEFT : Mouse.RIGHT;
        Click click = event.getClick() == ClickType.DOUBLE_CLICK ? Click.DOUBLE : event.isShiftClick() ? Click.SHIFT : Click.SINGLE;

        PolicyTarget target = event.getClickedInventory() == ui.asInventory() ? PolicyTarget.UI : PolicyTarget.INVENTORY;

        if (0 <= slot) {
            event.setCancelled(ui.getPolicy(slot, target) == SlotPolicy.FIXATION || event.isCancelled());
        } else {
            event.setCancelled((ui.getPolicy(slot, PolicyTarget.UI) == SlotPolicy.FIXATION && ui.getPolicy(slot, PolicyTarget.INVENTORY) == SlotPolicy.FIXATION) ||
                    event.isCancelled());
        }

        ItemStack item = Optional.ofNullable(event.getCurrentItem()).orElseGet(() -> event.getCursor().getType().isAir() ? null : event.getCursor());

        if (! event.isCancelled() && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && item != null) {
            event.setCancelled(true);

            Inventory inventory = target == PolicyTarget.UI ? player.getOpenInventory().getBottomInventory() : ui.asInventory();
            int remainingAmount = item.getAmount();

            for (int i = 0; i < inventory.getSize(); i ++) {
                if (ui.getPolicy(i, target == PolicyTarget.UI ? PolicyTarget.INVENTORY : PolicyTarget.UI) == SlotPolicy.FIXATION) {
                    continue;
                }

                ItemStack itemStack = inventory.getItem(i);

                if (itemStack != null && (! itemStack.isSimilar(item) || itemStack.getMaxStackSize() <= itemStack.getAmount())) {
                    continue;
                }

                int amount = Math.min(remainingAmount, itemStack != null ? itemStack.getMaxStackSize() - itemStack.getAmount() : item.getType().getMaxStackSize());
                int itemStackAmount = amount;

                if (itemStack == null) {
                    itemStack = new ItemStack(item);
                } else {
                    itemStackAmount += itemStack.getAmount();
                }

                itemStack.setAmount(itemStackAmount);

                if (inventory != ui.asInventory()|| ui.onClick(i, Click.QUICK_MOVE, mouse, item)) {
                    inventory.setItem(i, item);
                    remainingAmount -= amount;
                }

                if (remainingAmount <= 0) {
                    break;
                }
            }

            item.setAmount(remainingAmount);
            event.setCurrentItem(item);
        }

        if (target != PolicyTarget.UI) {
            return;
        }

        event.setCancelled(! ui.onClick(slot, click, mouse, item) || event.isCancelled());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(@NotNull InventoryDragEvent event) {
        if (! (event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ChestUI ui = Visualkit.getSessions(ChestUI.class, player);

        if (ui == null) {
            return;
        }

        event.setCancelled(event.getNewItems().entrySet().stream().anyMatch(entry -> {
            int raw = entry.getKey();
            int slot = player.getOpenInventory().convertSlot(raw);

            ItemStack item = entry.getValue();
            PolicyTarget target = raw < ui.getSize() ? PolicyTarget.UI : PolicyTarget.INVENTORY;

            return ui.getPolicy(slot, target) != SlotPolicy.VARIATION || (target == PolicyTarget.UI && ! ui.onClick(slot, Click.DRAG, Mouse.RIGHT, item));
        }) || event.isCancelled());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(@NotNull InventoryCloseEvent event) {
        Visualkit.getSessions(ContainerUI.class).stream()
                .filter(session -> session.asInventory() ==  event.getView().getTopInventory())
                .forEach(ContainerUI::close);
    }
}
