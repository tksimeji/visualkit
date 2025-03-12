package com.tksimeji.kunectron.element;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.markupextension.MarkupExtensionSupport;
import com.tksimeji.kunectron.markupextension.context.Context;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import com.tksimeji.kunectron.util.Components;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.*;

public class ItemElementImpl implements ItemElement, MarkupExtensionSupport {
    private @NotNull ItemStack itemStack;

    private @Nullable Component title;
    private @NotNull List<Component> lore = List.of();

    private @Nullable ItemSlotPolicy policy;

    private @Nullable Sound sound;
    private float soundVolume = 1.0F;
    private float soundPitch = 1.0F;

    private @Nullable Handler handler;

    private final boolean itemStackMode;

    private @Nullable Context<?> markupExtensionContext;

    public ItemElementImpl(final @NotNull ItemType type) {
        this(type.createItemStack(), false);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        Kunectron.adapter().fun_adp3uc(itemStack, itemMeta, Kunectron.plugin());
        itemStack.setItemMeta(itemMeta);
    }

    public ItemElementImpl(final @NotNull Material material) {
        this(material.asItemType());
    }

    public ItemElementImpl(final @NotNull ItemStack itemStack) {
        this(itemStack, true);
    }

    private ItemElementImpl(final @NotNull ItemStack itemStack, final boolean itemStackMode) {
        this.itemStack = itemStack;
        this.itemStackMode = itemStackMode;

        ItemMeta itemMeta = itemStack.getItemMeta();

        title(itemMeta.displayName());
        lore(itemMeta.hasLore() ? itemMeta.lore() : List.of());
        amount(itemStack.getAmount());
    }

    @Override
    public @Nullable Context<?> getContext() {
        return markupExtensionContext;
    }

    @Override
    public void setContext(@Nullable Context<?> ctx) {
        markupExtensionContext = ctx;
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
    public @NotNull Material material() {
        return itemStack.getType();
    }

    @Override
    public @NotNull ItemElement material(final @NotNull Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null.");
        itemStack = itemStack.withType(material);
        return this;
    }

    @Override
    public @NotNull Component title() {
        if (!itemStack.hasItemMeta()) {
            return Component.empty();
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        Component title = Optional.ofNullable(itemMeta.displayName()).orElse(Component.empty());
        return markupExtensionContext != null && Components.hasMarkupExtension(title) ? Components.markupExtension(title, markupExtensionContext) : title;
    }

    @Override
    public @NotNull ItemElement title(final @Nullable ComponentLike title) {
        if (!itemStack.hasItemMeta()) {
            return this;
        }

        this.title = title != null ? title.asComponent() : null;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName((title != null ? this.title : Component.empty()).colorIfAbsent(NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.setHideTooltip(!itemStackMode && this.title == null && this.lore.isEmpty());

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
        List<Component> lore = Optional.ofNullable(itemMeta.lore()).orElse(List.of());
        return markupExtensionContext != null && lore.stream().anyMatch(Components::hasMarkupExtension) ? lore.stream().map(component -> Components.markupExtension(component, markupExtensionContext)).toList() : lore;
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull Collection<Component> components) {
        Preconditions.checkArgument(components != null, "Components cannot be null.");
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
        itemMeta.setHideTooltip(!itemStackMode && this.title == null && this.lore.isEmpty());

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull ComponentLike... components) {
        Preconditions.checkArgument(components != null, "Components cannot be null.");
        return lore(Arrays.stream(components).toList());
    }

    @Override
    public @NotNull ItemElement lore(final @NotNull String... strings) {
        Preconditions.checkArgument(strings != null, "Strings cannot be null.");
        return lore(Arrays.stream(strings).map(string -> (ComponentLike) Component.text(string)).toList());
    }

    @Override
    public @Range(from = 1, to = Integer.MAX_VALUE) int amount() {
        return itemStack.getAmount();
    }

    @Override
    public @NotNull ItemElement amount(final @Range(from = 1, to = Integer.MAX_VALUE) int amount) {
        Preconditions.checkArgument(amount > 0, "Amount cannot be less then or equal to 0.");
        Preconditions.checkArgument(amount <= itemStack.getMaxStackSize(), "Amount must be less than or equal to the maximum stack size of " + itemStack.getMaxStackSize() + ".");
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
    public @NotNull ItemElement itemModel(final @Nullable Keyed itemModel) {
        if (!itemStack.hasItemMeta()) {
            return this;
        }

        Key key = itemModel != null ? itemModel.key() : null;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setItemModel(key != null ? new NamespacedKey(key.namespace(), key.value()) : null);
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

        if (aura) {
            itemMeta.addEnchant(Enchantment.INFINITY, 1, false);
        } else {
            itemMeta.removeEnchantments();
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @Override
    public @Nullable ItemSlotPolicy policy() {
        return policy;
    }

    @Override
    public @NotNull ItemElement policy(final @Nullable ItemSlotPolicy policy) {
        this.policy = policy;
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
    public @NotNull ItemStack create() {
        if (markupExtensionContext == null) {
            return itemStack.clone();
        }

        return ((ItemElementImpl) createCopy().title(title()).lore(lore())).itemStack;
    }

    @Override
    public @NotNull ItemStack create(final @NotNull Locale locale) {
        ItemStack itemStack = create();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return itemStack;
        }

        if (itemMeta.hasDisplayName()) {
            itemMeta.displayName(GlobalTranslator.render(Objects.requireNonNull(itemMeta.displayName()), locale));
        }

        if (itemMeta.hasLore()) {
            itemMeta.lore(Objects.requireNonNull(itemMeta.lore()).stream().map(component -> GlobalTranslator.render(component, locale)).toList());
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public @NotNull ItemElement createCopy() {
        ItemElementImpl copy = new ItemElementImpl(itemStack.clone(), itemStackMode);
        copy.title = title;
        copy.lore = lore;
        copy.policy = policy;
        copy.sound = sound;
        copy.soundVolume = soundVolume;
        copy.soundPitch = soundPitch;
        copy.handler = handler;

        copy.markupExtensionContext = markupExtensionContext;
        return copy;
    }
}
