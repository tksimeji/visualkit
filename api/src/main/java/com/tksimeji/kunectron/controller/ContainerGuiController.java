package com.tksimeji.kunectron.controller;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface ContainerGuiController<I extends Inventory> extends GuiController {
    @NotNull Player getPlayer();

    @NotNull I getInventory();

    void close();
}
