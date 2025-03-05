package com.tksimeji.visualkit.element;

import com.google.common.base.Preconditions;
import com.tksimeji.visualkit.Visualkit;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

class ItemElementImpl implements ItemElement {
    private @NotNull ItemStack itemStack;

    private @Nullable Component title;
    private @NotNull List<Component> lore = List.of();

    private @Nullable Sound sound;
    private float soundVolume = 1.0F;
    private float soundPitch = 1.0F;

    private @Nullable Handler handler;

    public ItemElementImpl(final @NotNull ItemType type) {
        this(type.createItemStack());
    }

    public ItemElementImpl(final @NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        Visualkit.adapter().fun_adp3uc(itemStack, itemMeta, Visualkit.plugin());
        itemStack.setItemMeta(itemMeta);

        title(title);
        lore(lore);
    }

    @Override
    public @NotNull ItemType type() {
        return itemStack.getType().asItemType();
    }

    @Override
    public @NotNull ItemElement type(final @NotNull ItemType type) {
        Preconditions.checkArgument(type != null, "Item type cannot be null.");
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

        this.title = title != null ? title.asComponent() : null;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName((title != null ? this.title : Component.empty()).colorIfAbsent(NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.setHideTooltip(this.title == null && this.lore.isEmpty());

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
    public @NotNull ItemElement lore(final @NotNull Collection<Component> components) {
        return lore(components.stream().map(component -> (ComponentLike) component).toList());
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull List<ComponentLike> components) {
        Preconditions.checkArgument(components != null, "Components cannot be null.");

        if (!itemStack.hasItemMeta()) {
            return this;
        }

        lore = components.stream()
                .map(component -> component.asComponent().colorIfAbsent(NamedTextColor.GRAY).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE))
                .toList();

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemMeta.setHideTooltip(this.title == null && this.lore.isEmpty());

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull ComponentLike... components) {
        return lore(Arrays.stream(components).toList());
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull String... strings) {
        return lore(Arrays.stream(strings).map(string -> (ComponentLike) Component.text(string)).toList());
    }

    @Override
    public @Range(from = 1, to = Integer.MAX_VALUE) int amount() {
        return itemStack.getAmount();
    }

    @Override
    public @NotNull ItemElement amount(final @Range(from = 1, to = Integer.MAX_VALUE) int amount) {
        Preconditions.checkArgument(0 < amount, "Amount cannot be less then or equal to 0.");
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
        itemMeta.setCustomModelData(0 <= customModelData ? customModelData : null);
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
        Preconditions.checkArgument(0 <= volume, "Volume cannot be less than 0.");
        Preconditions.checkArgument(0 <= pitch && pitch <= 2, "Pitch cannot be less than 0 or greater than 2.");

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

    @Override
    public @NotNull ItemElement handler(final @Nullable Handler3 handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public @NotNull ItemElement handler(final @Nullable Handler4 handler) {
        this.handler = handler;
        return this;
    }

    @ApiStatus.Internal
    @Override
    public @NotNull ItemStack create() {
        return itemStack.clone();
    }
}
