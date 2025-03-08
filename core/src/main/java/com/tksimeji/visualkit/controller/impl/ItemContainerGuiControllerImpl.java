package com.tksimeji.visualkit.controller.impl;

import com.tksimeji.visualkit.controller.ItemContainerGuiController;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class ItemContainerGuiControllerImpl<I extends Inventory> extends ContainerGuiControllerImpl<I> implements ItemContainerGuiController<I> {
    private final @NotNull Map<Integer, ItemElement> elements = new HashMap<>();

    public ItemContainerGuiControllerImpl(final @NotNull Object gui) {
        super(gui);
    }

    @Override
    public @Nullable ItemElement getElement(final int index) {
        return elements.get(index);
    }

    @Override
    public @NotNull Map<Integer, ItemElement> getElements() {
        return new HashMap<>(elements);
    }

    @Override
    public void setElement(final int index, final @Nullable ItemElement element) {
        ItemStack old = getInventory().getItem(index);
        if ((element == null && old == null) || (element != null && element.create().equals(old))) {
            return;
        }

        elements.put(index, element);
        getInventory().setItem(index, element != null ? element.create() : null);
    }

    @Override
    public void tick() {
        for (Map.Entry<Integer, ItemElement> entry : getElements().entrySet()) {
            setElement(entry.getKey(), entry.getValue());
        }
    }
}
