package com.tksimeji.visualkit.element;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ItemStackElementImpl implements ItemElement {
    private @NotNull ItemStack itemStack;

    private @Nullable Sound sound;
    private float soundVolume = 1.0F;
    private float soundPitch = 1.0F;

    private @Nullable Handler handler;

    public ItemStackElementImpl(final @NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public @NotNull ItemType type() {
        return itemStack.getType().asItemType();
    }

    @Override
    public @NotNull ItemElement type(final @NotNull ItemType type) {
        itemStack = itemStack.withType(type.createItemStack().getType());
        return this;
    }

    @Override
    public @NotNull Component title() {
        if (!itemStack.hasItemMeta()) {
            return Component.empty();
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        return Optional.ofNullable(itemMeta.displayName()).orElse(Component.empty());
    }

    @Override
    public @NotNull ItemElement title(final @Nullable ComponentLike title) {
        if (!itemStack.hasItemMeta()) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(title != null ? title.asComponent() : null);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Override
    public @NotNull ItemElement title(final @Nullable String title) {
        return title(title != null ? Component.text(title) : null);
    }

    @Override
    public @NotNull List<Component> lore() {
        if (!itemStack.hasItemMeta()) {
            return List.of();
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        return Optional.ofNullable(itemMeta.lore()).orElse(List.of());
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull Collection<ComponentLike> components) {
        if (!itemStack.hasItemMeta()) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(components.stream().map(ComponentLike::asComponent).toList());
        return this;
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull ComponentLike... components) {
        return lore(Arrays.stream(components).toList());
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull String... strings) {
        return lore(Arrays.stream(strings).map(Component::text).collect(Collectors.toList()));
    }

    @Override
    public @Range(from = 1, to = Integer.MAX_VALUE) int amount() {
        return itemStack.getAmount();
    }

    @Override
    public @NotNull ItemElement amount(final @Range(from = 1, to = Integer.MAX_VALUE) int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    @Override
    public int customModelData() {
        if (!itemStack.hasItemMeta() || itemStack.getItemMeta().hasCustomModelData()) {
            return -1;
        }

        return itemStack.getItemMeta().getCustomModelData();
    }

    @Override
    public @NotNull ItemElement customModelData(final int customModelData) {
        if (!itemStack.hasItemMeta()) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(customModelData);
        return this;
    }

    @Override
    public @Nullable Key itemModel() {
        return itemStack.hasItemMeta() ? itemStack.getItemMeta().getItemModel() : null;
    }

    @Override
    public @NotNull ItemElement itemModel(final @Nullable Key itemModel) {
        if (!itemStack.hasItemMeta()) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setItemModel(itemModel != null ? new NamespacedKey(itemModel.namespace(), itemModel.value()) : null);
        return this;
    }

    @Override
    public boolean aura() {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().hasEnchants();
    }

    @Override
    public @NotNull ItemElement aura(final boolean aura) {
        if (!itemStack.hasItemMeta()) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.INFINITY, 1, false);
        return this;
    }

    @Override
    public @Nullable Sound sound() {
        return sound;
    }

    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) float soundVolume() {
        return soundVolume;
    }

    @Override
    public @Range(from = 0, to = 2) float soundPitch() {
        return soundPitch;
    }

    @Override
    public @NotNull ItemElement sound(final @Nullable Sound sound) {
        return sound(sound, soundVolume, soundPitch);
    }

    @Override
    public @NotNull ItemElement sound(final @Nullable Sound sound, final @Range(from = 0, to = Integer.MAX_VALUE) float volume, final @Range(from = 0, to = 2) float pitch) {
        this.sound = sound;
        soundVolume = volume;
        soundPitch = pitch;
        return this;
    }

    @Override
    public @Nullable Handler handler() {
        return handler;
    }

    @Override
    public @NotNull ItemElement handler(final @Nullable Handler1 handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public @NotNull ItemElement handler(final @Nullable Handler2 handler) {
        this.handler = handler;
        return this;
    }
}
