package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Size;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface IChestUI extends IInventoryUI<Inventory> {
    @NotNull Size size();
}
