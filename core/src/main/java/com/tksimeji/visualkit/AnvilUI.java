package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Action;
import com.tksimeji.visualkit.api.Mouse;
import com.tksimeji.visualkit.util.ComponentUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

@ApiStatus.Experimental
public abstract class AnvilUI extends ContainerUI<AnvilInventory> implements IAnvilUI {
    protected @NotNull AnvilInventory inventory;

    private final int level;
    private final float exp;

    public AnvilUI(@NotNull Player player) {
        super(player);

        level = player.getLevel();
        exp = player.getExp();

        player.setLevel(1);
        player.setExp(0);

        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> {
            inventory = Objects.requireNonNull(Visualkit.adapter()).fun_u32qi0(player, title());
            inventory.setItem(0, getDummyItem());
        });
    }

    public @NotNull ItemStack getDummyItem() {
        ItemStack dummy = new ItemStack(dummy(), 1);
        ItemMeta meta = dummy.getItemMeta();
        meta.displayName(ComponentUtility.empty().append(Component.text(Optional.ofNullable(placeholder()).orElse(""))));
        dummy.setItemMeta(meta);
        return dummy;
    }

    @Override
    public @NotNull AnvilInventory asInventory() {
        return inventory;
    }

    @Override
    public final boolean onClick(int slot, @NotNull Action action, @NotNull Mouse mouse, @Nullable ItemStack item) {
        if (slot == 2) {
            player.closeInventory();
        }

        return false;
    }

    @Override
    public void onClose() {
        player.setLevel(level);
        player.setExp(exp);

        ItemStack item = inventory.getItem(2);
        inventory.clear();

        if (item == null || ! item.hasItemMeta()) {
            return;
        }

        Component component = item.getItemMeta().displayName();
        String string = component != null ? PlainTextComponentSerializer.plainText().serialize(component) : "";

        onTyped(string);
    }

    @Override
    public final void close() {
        super.close();
    }
}
