package com.tksimeji.visualkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
public abstract class ContainerUI<I extends Inventory> extends VisualkitUI implements IContainerUI<I> {
    protected final @NotNull Player player;

    /**
     * Creating a GUI.
     *
     * @param player Player showing GUI
     */
    public ContainerUI(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public final @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public final int getSize() {
        return asInventory().getSize();
    }

    @Override
    public void close() {
        onClose();

        Visualkit.sessions.remove(this);

        if (! player.getOpenInventory().getTopInventory().isEmpty() &&
                Visualkit.getSessions(ContainerUI.class).stream().noneMatch(s -> s.getPlayer() == player)) {
            player.closeInventory();
        }

        player.updateInventory();
    }
}
