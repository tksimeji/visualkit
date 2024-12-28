package com.tksimeji.visualkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryUI<I extends Inventory> extends VisualkitUI implements IInventoryUI<I> {
    protected final @NotNull Player player;

    /**
     * Creating a GUI.
     *
     * @param player Player showing GUI
     */
    public InventoryUI(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public final @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public int getSize() {
        return asInventory().getSize();
    }

    @Override
    public final void close() {
        onClose();

        Visualkit.sessions.remove(this);

        if (! player.getOpenInventory().getTopInventory().isEmpty() &&
                Visualkit.sessions(InventoryUI.class).stream().noneMatch(s -> s.getPlayer() == player)) {
            player.closeInventory();
        }

        player.updateInventory();
    }
}
