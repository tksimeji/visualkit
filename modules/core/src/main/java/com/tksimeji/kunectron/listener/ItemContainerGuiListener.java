package com.tksimeji.kunectron.listener;

import com.tksimeji.kunectron.Action;
import com.tksimeji.kunectron.Mouse;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.ItemContainerGuiController;
import com.tksimeji.kunectron.element.Element;
import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import com.tksimeji.kunectron.policy.Policy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemContainerGuiListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(final @NotNull InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemContainerGuiController<?> controller = controller(event.getInventory());

        if (controller == null) {
            return;
        }

        event.setCancelled(true);

        int index = event.getRawSlot();
        ItemSlotPolicy policy = index >= 0 ? controller.getPolicy(index) : Policy.itemSlot(true);

        if (controller.isValidIndex(index)) {
            Action action = event.getClick() == ClickType.DOUBLE_CLICK ? Action.DOUBLE_CLICK : event.isShiftClick() ? Action.SHIFT_CLICK : Action.SINGLE_CLICK;
            Mouse mouse = event.getClick().isLeftClick() ? Mouse.LEFT : Mouse.RIGHT;
            controller.click(index, action, mouse);
        }

        if (policy.isFixation()) {
            return;
        }

        ClickProcessor processor = new ClickProcessor(event, controller, player);

        switch (event.getAction()) {
            case CLONE_STACK, DROP_ALL_CURSOR, DROP_ONE_CURSOR -> event.setCancelled(false);
            case DROP_ALL_SLOT -> processor.dropAllSlot();
            case DROP_ONE_SLOT -> processor.dropOneSlot();
            case HOTBAR_SWAP -> processor.hotbarSwap();
            case MOVE_TO_OTHER_INVENTORY -> processor.moveToOtherInventory();
            case PICKUP_ALL -> processor.pickupAll();
            case PICKUP_HALF -> processor.pickupHalf();
            case PLACE_ALL -> processor.placeAll();
            case PLACE_ONE -> processor.placeOne();
            case PLACE_SOME -> processor.placeSome();
            case SWAP_WITH_CURSOR -> processor.swapWithCursor();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryDrag(final @NotNull InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        ItemContainerGuiController<?> controller = controller(event.getInventory());

        if (controller == null) {
            return;
        }

        event.setCancelled(event.getRawSlots().stream().anyMatch(index -> controller.getPolicy(index).isFixation()));
    }

    @ApiStatus.Internal
    private @Nullable ItemContainerGuiController<?> controller(final @Nullable Inventory inventory) {
        return Kunectron.getGuiControllers()
                .stream()
                .filter(controller -> (controller instanceof ItemContainerGuiController<?> containerGuiController) && containerGuiController.getInventory() == inventory)
                .map(controller -> (ItemContainerGuiController<?>) controller)
                .findFirst()
                .orElse(null);
    }

    private static class ClickProcessor {
        private final @NotNull InventoryClickEvent event;
        private final @NotNull ItemContainerGuiController<?> controller;
        private final @NotNull Player player;
        private final int index;

        public ClickProcessor(final @NotNull InventoryClickEvent event, final @NotNull ItemContainerGuiController<?> controller, final @NotNull Player player) {
            this.event = event;
            this.controller = controller;
            this.player = player;
            index = event.getRawSlot();
        }

        public void dropAllSlot() {
            event.setCancelled(false);

            if (controller.isValidIndex(index)) {
                ItemStack itemStack = event.getCurrentItem();
                controller.setElement(index, null);
                event.setCurrentItem(itemStack);
            }
        }

        public void dropOneSlot() {
            event.setCancelled(false);

            ItemElement element = controller.getElement(event.getRawSlot());

            if (element != null) {
                if (element.amount() >= 2) {
                    element.amount(element.amount() - 1);
                } else {
                    dropAllSlot();
                }
            }
        }

        public void hotbarSwap() {
            event.setCancelled(false);

            ItemStack itemStack = event.getCurrentItem();
            ItemStack hotbarItemStack = player.getInventory().getItem(event.getHotbarButton());

            controller.setElement(event.getRawSlot(), hotbarItemStack != null ? Element.item(hotbarItemStack) : null);
            event.setCurrentItem(itemStack);
        }

        public void moveToOtherInventory() {
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack == null) {
                return;
            }

            if (controller.isValidIndex(index)) {
                controller.setElement(index, null);
                event.setCancelled(false);
                event.setCurrentItem(itemStack);
                return;
            }

            int remaining = itemStack.getAmount();

            for (int i = 0; i < controller.getSize(); i++) {
                if (remaining <= 0) {
                    break;
                }

                ItemElement element = controller.getElement(i);
                ItemStack elementItemStack = element != null ? element.create(player.locale()) : null;

                if (controller.getPolicy(i).isFixation() || (elementItemStack != null && !elementItemStack.isSimilar(itemStack))) {
                    continue;
                }

                if (element == null) {
                    controller.setElement(i, Element.item(itemStack).amount(remaining));
                    remaining -= remaining;
                    break;
                }

                int amount = Math.min(remaining, elementItemStack.getMaxStackSize() - element.amount());
                element.amount(element.amount() + amount);
                remaining -= amount;
            }

            if (remaining <= 0) {
                event.setCurrentItem(null);
            } else {
                itemStack.setAmount(remaining);
                event.setCurrentItem(itemStack);
            }
        }

        public void pickupAll() {
            event.setCancelled(false);

            if (controller.isValidIndex(index)) {
                ItemStack itemStack = event.getCurrentItem();
                controller.setElement(index, null);
                event.setCurrentItem(itemStack);
            }
        }

        public void pickupHalf() {
            event.setCancelled(false);
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack == null) {
                return;
            }

            if (controller.isValidIndex(index)) {
                ItemElement element = controller.getElement(index);

                if (element != null) {
                    int remaining = element.amount() / 2;
                    if (remaining > 0) {
                        element.amount(remaining);
                    } else {
                        controller.setElement(index, null);
                        event.setCurrentItem(itemStack);
                    }
                }
            }
        }

        public void placeAll() {
            event.setCancelled(false);
            ItemStack itemStack = event.getCurrentItem();
            ItemStack cursor = event.getCursor();

            if (controller.isValidIndex(index)) {
                ItemElement element = controller.getElement(index);

                if (element == null) {
                    controller.setElement(index, Element.item(cursor));
                } else {
                    element.amount(element.amount() + cursor.getAmount());
                }

                event.setCurrentItem(itemStack);
            }
        }

        public void placeOne() {
            event.setCancelled(false);

            if (controller.isValidIndex(index)) {
                ItemElement element = controller.getElement(index);

                if (element != null) {
                    element.amount(element.amount() + 1);
                } else {
                    ItemStack itemStack = event.getCursor().clone();
                    itemStack.setAmount(1);
                    controller.setElement(index, Element.item(itemStack));
                }
            }
        }

        public void placeSome() {
            event.setCancelled(false);
            ItemElement element = controller.getElement(index);
            ItemStack elementItemStack = element != null ? element.create(player.locale()) : null;

            if (!controller.isValidIndex(index) || elementItemStack == null) {
                return;
            }

            ItemStack cursorItemStack = event.getCursor();
            int amount = Math.min(elementItemStack.getMaxStackSize(), element.amount() + cursorItemStack.getAmount());
            element.amount(amount);
        }

        public void swapWithCursor() {
            event.setCancelled(false);

            if (controller.isValidIndex(index)) {
                ItemStack itemStack = event.getCurrentItem();
                ItemStack cursor = event.getCursor();
                controller.setElement(index, Element.item(cursor));
                event.setCurrentItem(itemStack);
            }
        }
    }
}
