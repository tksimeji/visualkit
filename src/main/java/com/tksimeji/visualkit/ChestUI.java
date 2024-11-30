package com.tksimeji.visualkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class ChestUI extends InventoryUI<Inventory> implements IChestUI {
    protected final Inventory inventory;

    public ChestUI(@NotNull Player player) {
        super(player);
        player.openInventory(inventory = Bukkit.createInventory(null, size().asInt(), title()));
    }

    @Override
    public @NotNull Inventory asInventory() {
        return inventory;
    }
}
