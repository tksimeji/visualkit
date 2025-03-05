package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.impl.GuiControllerImpl;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class InventoryGuiControllerImpl extends GuiControllerImpl implements InventoryGuiController {
    private final @NotNull Map<Integer, ItemElement> elementMap = new HashMap<>();

    public InventoryGuiControllerImpl(@NotNull Object gui) {
        super(gui);
    }

    @Override
    public @Nullable ItemElement getElement(final int index) {
        return elementMap.get(index);
    }

    @Override
    public @NotNull Map<Integer, ItemElement> getElements() {
        return new HashMap<>(elementMap);
    }

    @Override
    public void setElement(final int index, final @Nullable ItemElement element) {
        ItemStack old = getInventory().getItem(index);
        if ((element == null && old == null) || (element != null && element.create().equals(old))) {
            return;
        }

        elementMap.put(index, element);
        getInventory().setItem(index, element != null ? element.create() : null);
    }

    @Override
    public void close() {
        Visualkit.removeGuiController(this);

        if (getPlayer().getOpenInventory().getTopInventory().getType() != InventoryType.CRAFTING &&
                Visualkit.getGuiControllers().stream().noneMatch(controller -> controller instanceof InventoryGuiController && ((InventoryGuiController) controller).getPlayer() == getPlayer())) {
            getPlayer().closeInventory();
        }

        getPlayer().updateInventory();
    }

    @Override
    public void tick() {
        for (Map.Entry<Integer, ItemElement> entry : getElements().entrySet()) {
            setElement(entry.getKey(), entry.getValue());
        }
    }
}
