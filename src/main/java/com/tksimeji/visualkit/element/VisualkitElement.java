package com.tksimeji.visualkit.element;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;

public final class VisualkitElement implements Closeable {
    public static @NotNull VisualkitElement of(@NotNull Material type) {
        return new VisualkitElement(type);
    }

    private @NotNull ItemStack item;

    private VisualkitElement(@NotNull Material type)
    {
        this.type = type;
        item = new ItemStack(type, stack);
    }

    private @NotNull Material type;

    public @NotNull VisualkitElement type(@NotNull Material type) {
        this.type = type;
        return this;
    }

    private @NotNull Xmpl title = Xmpl.empty();

    public @NotNull VisualkitElement title(@Nullable Component title) {
        this.title.close();
        this.title = title != null ? new Xmpl(title) : Xmpl.empty();
        return this;
    }

    private @NotNull Lore lore = Lore.empty();

    public @NotNull VisualkitElement lore(@NotNull Component... components) {
        this.lore.close();
        this.lore = new Lore(components);
        return this;
    }

    private int stack = 1;

    public @NotNull VisualkitElement stack(int stack) {
        this.stack = Math.max(Math.min(stack, 1), this.type.getMaxStackSize());
        return this;
    }

    private int model = -1;

    public @NotNull VisualkitElement model(int model) {
        this.model = model;
        return this;
    }

    private boolean aura = false;

    public @NotNull VisualkitElement aura(boolean aura) {
        this.aura = aura;
        return this;
    }

    @Override
    public void close() {
        title.close();
        lore.close();
    }

    public @NotNull ItemStack asItemStack(@NotNull Object object) {
        title.setTarget(object);
        lore.setTarget(object);

        ItemStack item = this.item.getType() != type ? (this.item = new ItemStack(type, stack)) : this.item;
        item.setAmount(stack);

        ItemMeta meta = item.getItemMeta();
        meta.displayName(title.asComponent());
        meta.lore(lore.asComponentList());
        meta.setCustomModelData(0 <= model ? model : null);
        meta.addItemFlags(ItemFlag.values());
        meta.removeEnchantments();

        if (aura) {
            meta.addEnchant(Enchantment.INFINITY, 1, false);
        }

        item.setItemMeta(meta);
        return item;
    }
}
