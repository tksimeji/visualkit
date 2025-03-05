package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.Tickable;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public sealed interface ChestGuiController extends InventoryGuiController, Tickable permits ChestGuiControllerImpl {
    @Nullable ItemElement getElement(final int index);

    @NotNull Map<Integer, ItemElement> getElements();

    void setElement(final int index, final @Nullable ItemElement element);

    @NotNull Inventory getInventory();

    boolean click(final int index, final @NotNull Action action, final @NotNull Mouse mouse);
}
