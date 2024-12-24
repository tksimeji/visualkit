package com.tksimeji.visualkit.element;

import com.google.common.collect.Lists;
import com.tksimeji.visualkit.Killable;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.api.Click;
import com.tksimeji.visualkit.api.Mouse;
import com.tksimeji.visualkit.xmpl.XmplTarget;
import com.tksimeji.visualkit.xmpl.Xmpl;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class VisualkitElement implements Killable {
    /**
     * Create a new element.
     *
     * @param type Item type
     * @return New element
     */
    public static @NotNull VisualkitElement create(@NotNull Material type) {
        return new VisualkitElement(type);
    }

    /**
     * Create a new head element.
     *
     * @return New element
     */
    public static @NotNull HeadElement head() {
        return new HeadElement();
    }

    /**
     * Create a new head element.
     *
     * @param url Texture URL
     * @return New element
     */
    public static @NotNull HeadElement head(@NotNull String url) {
        return head().url(url);
    }

    /**
     * Create a new head element.
     *
     * @param uuid Player UUID
     * @return New element
     */
    public static @NotNull HeadElement head(@NotNull UUID uuid) {
        return head().uuid(uuid);
    }

    /**
     * Create a new head element.
     *
     * @param player Player name
     * @return New element
     */
    public static @NotNull HeadElement head(@NotNull OfflinePlayer player) {
        return head().player(player);
    }

    /**
     * Create a new element from the {@link ItemStack}.
     *
     * @param item The source {@link ItemStack}
     * @return New element
     */
    public static @NotNull VisualkitElement of(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        VisualkitElement element = create(item.getType());

        element.title(meta.displayName());
        element.stack(item.getAmount());

        if (meta.hasLore()) {
            element.lore(meta.lore().toArray(new Component[0]));
        }

        if (meta.hasCustomModelData()) {
            element.model(meta.getCustomModelData());
        }

        if (meta.hasEnchants()) {
            element.aura(true);
        }

        return element;
    }

    private @NotNull ItemStack item;

    protected VisualkitElement(@NotNull Material type) {
        this.type = type;
        item = new ItemStack(type, stack);
    }

    private @NotNull Material type;

    /**
     * Get the item type.
     *
     * @return Item type
     */
    public @NotNull Material type() {
        return type;
    }

    /**
     * Set the item type.
     *
     * @param type Item type
     * @return Updated element
     */
    public @NotNull VisualkitElement type(@NotNull Material type) {
        this.type = type;
        return this;
    }

    private @NotNull Xmpl title = Xmpl.empty();

    /**
     * Get the title
     *
     * @return Title
     */
    public @NotNull Component title() {
        return title.asComponent();
    }

    /**
     * Set the title.
     *
     * @param title Title
     * @return Updated element
     */
    public @NotNull VisualkitElement title(@Nullable Component title) {
        this.title.kill();
        this.title = title != null ? new Xmpl(title) : Xmpl.empty();
        return this;
    }

    private @NotNull Lore lore = Lore.empty();

    /**
     * Get the lore.
     *
     * @return Lore
     */
    public @NotNull List<Component> lore() {
        return lore.asComponentList();
    }

    /**
     * Set the lore.
     *
     * @param components Lore
     * @return Updated element
     */
    public @NotNull VisualkitElement lore(@NotNull Component... components) {
        this.lore.kill();
        this.lore = new Lore(components);
        return this;
    }

    private int stack = 1;

    /**
     * Get the stack count.
     *
     * @return Stack count
     */
    public int stack() {
        return stack;
    }

    /**
     * Set the stack count.
     *
     * @param stack Stack count
     * @return Updated element
     */
    public @NotNull VisualkitElement stack(int stack) {
        this.stack = Math.max(Math.min(stack, this.type.getMaxStackSize()), 1);
        return this;
    }

    private int model = -1;

    /**
     * Get the custom model data.
     *
     * @return Custom model data
     */
    public int model() {
        return model;
    }

    /**
     * Set the custom model data.
     *
     * @param model Custom model data
     * @return Updated element
     */
    public @NotNull VisualkitElement model(int model) {
        this.model = model;
        return this;
    }

    private boolean aura = false;

    /**
     * Get whether the item has an enchantment aura.
     *
     * @return True if has aura
     */
    public boolean aura() {
        return aura;
    }

    /**
     * Set whether to have an enchantment aura.
     *
     * @param aura True if has aura
     * @return Updated element
     */
    public @NotNull VisualkitElement aura(boolean aura) {
        this.aura = aura;
        return this;
    }

    private @Nullable Handler handler = null;

    /**
     * Get the handler to be called on click.
     *
     * @return Handler
     */
    public @Nullable Handler handler() {
        return handler;
    }

    /**
     * Set the handler to be called on click.
     *
     * @param handler Handler
     * @return Updated element
     */
    public @NotNull VisualkitElement handler(@NotNull Handler1 handler) {
        this.handler = handler;
        return this;
    }

    /**
     * Set the handler to be called on click.
     *
     * @param handler Handler
     * @return Updated element
     */
    public @NotNull VisualkitElement handler(@NotNull Handler2 handler) {
        this.handler = handler;
        return this;
    }

    private @Nullable Sound sound;
    private float volume = 1.0f;
    private float pitch = 1.0f;

    /**
     * Get the sound to be played on click.
     *
     * @return Sound
     */
    public @Nullable Sound sound() {
        return sound;
    }

    /**
     * Get the volume of the sound being played.
     *
     * @return Sound volume
     */
    public float volume() {
        return volume;
    }

    /**
     * Get the pitch of the sound being played.
     *
     * @return Sound pitch
     */
    public float pitch() {
        return pitch;
    }

    /**
     * Set the sound to be played on click.
     *
     * @param sound Sound
     * @return Updated element
     */
    public @NotNull VisualkitElement sound(@NotNull Sound sound) {
        return sound(sound, 1.0f, 1.0f);
    }

    /**
     * Set the sound to be played on click.
     *
     * @param sound Sound
     * @param volume Sound volume
     * @param pitch Sound pitch
     * @return Updated element
     */
    public @NotNull VisualkitElement sound(@NotNull Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        return this;
    }

    @Override
    public void kill() {
        title.kill();
        lore.kill();
    }

    /**
     * Build an ItemStack
     *
     * @param target XMPL Target
     * @return ItemStack
     */
    public @NotNull ItemStack asItemStack(@NotNull XmplTarget target) {
        title.setTarget(target);
        lore.setTarget(target);

        ItemStack item = this.item.getType() != type ? (this.item = new ItemStack(type, stack)) : this.item;
        item.setAmount(stack);

        ItemMeta meta = item.getItemMeta();
        meta.displayName(title.asComponent());
        meta.lore(lore.asComponentList());
        meta.setCustomModelData(0 <= model ? model : null);
        meta.addItemFlags(ItemFlag.values());
        meta.removeEnchantments();

        meta.setHideTooltip((meta.displayName() == null && ! meta.hasLore()) || meta.isHideTooltip());

        Arrays.stream(Lists.newArrayList(Registry.ATTRIBUTE).toArray(new Attribute[0])).forEach(attribute -> {
            meta.removeAttributeModifier(attribute);
            meta.addAttributeModifier(attribute, new AttributeModifier(new NamespacedKey(Visualkit.plugin(), attribute.getKey().getKey()), 0, AttributeModifier.Operation.ADD_NUMBER));
        });

        if (aura) {
            meta.addEnchant(Enchantment.INFINITY, 1, false);
        }

        item.setItemMeta(meta);
        return item;
    }

    public interface Handler {}

    public interface Handler1 extends Handler {
        void onClick(int slot, @NotNull Click click, @NotNull Mouse mouse);
    }

    public interface Handler2 extends Handler {
        void onClick();
    }
}
