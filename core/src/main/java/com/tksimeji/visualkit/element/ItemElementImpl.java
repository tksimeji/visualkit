package com.tksimeji.visualkit.element;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.*;
import java.util.stream.Collectors;

class ItemElementImpl implements ItemElement {
    private @NotNull ItemType type;

    private @NotNull Component title = Component.empty();

    private @NotNull List<Component> lore = List.of();

    private int amount = 1;

    private int customModelData;

    private @Nullable Key itemModel;

    private boolean aura;

    private @Nullable Sound sound;
    private float soundVolume = 1.0F;
    private float soundPitch = 1.0F;

    private @Nullable Handler handler;

    public ItemElementImpl(final @NotNull ItemType type) {
        this.type = type;
    }

    @Override
    public @NotNull ItemType type() {
        return type;
    }

    @Override
    public @NotNull ItemElement type(final @NotNull ItemType type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull Component title() {
        return title;
    }

    @Override
    public @NotNull ItemElement title(final @Nullable ComponentLike title) {
        this.title = Optional.ofNullable(title).map(ComponentLike::asComponent).orElse(Component.empty());
        return this;
    }

    @Override
    public @NotNull ItemElement title(final @Nullable String title) {
        return title(Optional.ofNullable(title).map(Component::text).orElse(null));
    }

    @Override
    public @NotNull List<Component> lore() {
        return lore.stream().toList();
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull Collection<ComponentLike> components) {
        this.lore = components.stream().map(ComponentLike::asComponent).toList();
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
        return amount;
    }

    @Override
    public @NotNull ItemElement amount(final @Range(from = 1, to = Integer.MAX_VALUE) int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public int customModelData() {
        return customModelData;
    }

    @Override
    public @NotNull ItemElement customModelData(final int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    @Override
    public @Nullable Key itemModel() {
        return itemModel;
    }

    @Override
    public @NotNull ItemElement itemModel(final @Nullable Key itemModel) {
        this.itemModel = itemModel;
        return this;
    }

    @Override
    public boolean aura() {
        return aura;
    }

    @Override
    public @NotNull ItemElement aura(final boolean aura) {
        this.aura = aura;
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
