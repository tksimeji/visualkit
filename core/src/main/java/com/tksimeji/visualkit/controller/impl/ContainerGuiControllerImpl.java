package com.tksimeji.visualkit.controller.impl;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.ContainerGuiController;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class ContainerGuiControllerImpl<I extends Inventory> extends GuiControllerImpl implements ContainerGuiController<I> {
    public ContainerGuiControllerImpl(@NotNull Object gui) {
        super(gui);
    }

    @Override
    public void close() {
        Visualkit.removeGuiController(this);

        if (getPlayer().getOpenInventory().getTopInventory().getType() != InventoryType.CRAFTING &&
                Visualkit.getGuiControllers().stream().noneMatch(controller -> controller instanceof ContainerGuiController && ((ContainerGuiController) controller).getPlayer() == getPlayer())) {
            getPlayer().closeInventory();
        }

        getPlayer().updateInventory();
    }
}
