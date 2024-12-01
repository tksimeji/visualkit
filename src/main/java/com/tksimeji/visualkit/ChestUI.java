package com.tksimeji.visualkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class ChestUI extends InventoryUI<Inventory> implements IChestUI {
    protected final @NotNull Inventory inventory;

    /**
     * Start a GUI for any player.
     *
     * @param player Player showing GUI
     */
    public ChestUI(@NotNull Player player) {
        super(player);
        inventory = Bukkit.createInventory(null, size().asInt(), title());
        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> player.openInventory(inventory));
    }

    @Override
    public final @NotNull Inventory asInventory() {
        return inventory;
    }
}
