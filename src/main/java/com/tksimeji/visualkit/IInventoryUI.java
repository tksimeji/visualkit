package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Click;
import com.tksimeji.visualkit.api.Mouse;
import com.tksimeji.visualkit.element.VisualkitElement;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IInventoryUI<I extends Inventory> extends VisualkitUI {
    /**
     * Defines the UI title.
     *
     * @return UI title
     */
    @NotNull Component title();

    /**
     * Gets the player that has the GUI open.
     *
     * @return player
     */
    @NotNull Player getPlayer();

    /**
     * Gets the element of a given slot.
     *
     * @param slot Slot index number
     * @return GUI element
     */
    @Nullable VisualkitElement getElement(int slot);

    /**
     * Place an element in any slot.
     *
     * @param slot    Slot index number
     * @param element The element to place, if null it will become an empty slot
     */
    void setElement(int slot, @Nullable VisualkitElement element);

    /**
     * Place on element in any slot.
     *
     * @param slot    Slot index number
     * @param element The element to place, if null it will become an empty slot
     */
    void setElement(int slot, @Nullable ItemStack element);

    /**
     * Get GUI inventory.
     *
     * @return Inventory
     */
    @NotNull I asInventory();

    /**
     * Called when the GUI is clicked.
     *
     * @param slot  Clicked slot
     * @param click Click Type
     * @param mouse The mouse button used
     */
    default void onClick(int slot, @NotNull Click click, @NotNull Mouse mouse) {
    }

    /**
     * Called when the GUI is closed.
     */
    default void onClose() {
    }

    /**
     * Close the GUI.
     */
    void close();
}
