package com.tksimeji.visualkit.controller;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface InventoryGuiController extends GuiController {
    @NotNull Player getPlayer();

    void close();
}
