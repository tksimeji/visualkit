package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Click;
import com.tksimeji.visualkit.api.Mouse;
import com.tksimeji.visualkit.element.VisualkitElement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IInventoryUI<I extends Inventory> extends VisualkitUI {
    @NotNull Player getPlayer();

    @Nullable VisualkitElement getElement(int slot);

    void setElement(int slot, @Nullable VisualkitElement element);

    @NotNull I asInventory();

    default void onClick(int slot, @NotNull Click click, @NotNull Mouse mouse) {}

    default void onClose() {}

    default void onTick() {}

    void close();
}
