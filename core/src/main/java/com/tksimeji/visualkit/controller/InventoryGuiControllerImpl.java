package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.impl.GuiControllerImpl;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryGuiControllerImpl extends GuiControllerImpl implements InventoryGuiController {
    public InventoryGuiControllerImpl(@NotNull Object gui) {
        super(gui);
    }

    @Override
    public void close() {
        Visualkit.removeGuiController(this);

        if (!getPlayer().getOpenInventory().getTopInventory().isEmpty() &&
                Visualkit.getGuiControllers().stream().noneMatch(controller -> controller instanceof InventoryGuiController && ((InventoryGuiController) controller).getPlayer() == getPlayer())) {
            getPlayer().closeInventory();
        }

        getPlayer().updateInventory();
    }
}
