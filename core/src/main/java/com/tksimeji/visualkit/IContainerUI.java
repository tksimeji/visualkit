package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Action;
import com.tksimeji.visualkit.api.Mouse;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IContainerUI<I extends Inventory> extends IVisualkitUI {
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
     * Gets the size of the inventory
     *
     * @return Inventory size
     */
    int getSize();

    /**
     * Gets GUI inventory.
     *
     * @return Inventory
     */
    @NotNull I asInventory();

    /**
     * Called when the GUI is clicked.
     *
     * @param slot  Clicked slot
     * @param action Click Type
     * @param mouse The mouse button used
     * @param item Clicked item
     */
    default boolean onClick(int slot, @NotNull Action action, @NotNull Mouse mouse, @Nullable ItemStack item) {
        return true;
    }

    /**
     * Called when the GUI is closed.
     */
    default void onClose() {}

    /**
     * Close the GUI.
     */
    void close();
}
