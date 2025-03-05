package com.tksimeji.visualkit.type;

import com.tksimeji.visualkit.ChestGui;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.ChestGuiController;
import com.tksimeji.visualkit.controller.ChestGuiControllerImpl;
import com.tksimeji.visualkit.controller.GuiController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class ChestGuiType implements GuiType<ChestGui, ChestGuiController>, Listener {
    @Override
    public @NotNull Class<ChestGui> getAnnotationClass() {
        return ChestGui.class;
    }

    @NotNull
    @Override
    public Class<ChestGuiController> getControllerClass() {
        return ChestGuiController.class;
    }

    @Override
    public @NotNull ChestGuiController createController(final @NotNull Object object, final @NotNull ChestGui annotation) {
        return new ChestGuiControllerImpl(annotation, object);
    }

    @EventHandler
    public void onInventoryClick(final @NotNull InventoryClickEvent event) {
        for (GuiController controller : Visualkit.getGuiControllers()) {
        }
    }
}
