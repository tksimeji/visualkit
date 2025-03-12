package com.tksimeji.kunectron.controller.impl;

import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.ContainerGuiController;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class ContainerGuiControllerImpl<I extends Inventory> extends GuiControllerImpl implements ContainerGuiController<I> {
    public ContainerGuiControllerImpl(@NotNull Object gui) {
        super(gui);
    }

    @Override
    public void close() {
        Kunectron.removeGuiController(this);

        if (getPlayer().getOpenInventory().getTopInventory().getType() != InventoryType.CRAFTING &&
                Kunectron.getGuiControllers().stream().noneMatch(controller -> controller instanceof ContainerGuiController && ((ContainerGuiController) controller).getPlayer() == getPlayer())) {
            getPlayer().closeInventory();
        }

        getPlayer().updateInventory();
    }
}
