package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.util.ComponentUtility;
import com.tksimeji.visualkit.xmpl.XmplTarget;
import com.tksimeji.visualkit.xmpl.Xmpl;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Deprecated(since = "1.0.0", forRemoval = true)
public class VisualkitElement implements IVisualkitElement<VisualkitElement> {
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
     * Create a new item stack element.
     *
     * @param itemStack ItemStack
     * @return new element
     */
    public static @NotNull ItemStackElement item(@NotNull ItemStack itemStack) {
        return new ItemStackElement(itemStack);
    }

    private @NotNull ItemStack item;

    protected VisualkitElement(@NotNull Material type) {
        this.type = type;
        item = new ItemStack(type, stack);
    }

    private @NotNull Material type;

    @Override
    public @NotNull Material type() {
        return type;
    }

    /**
     * Sets the item type.
     *
     * @param type Item type
     * @return Updated element
     */
    public @NotNull VisualkitElement type(@NotNull Material type) {
        this.type = type;
        return this;
    }

    private @Nullable Xmpl title = null;

    @Override
    public @NotNull Component title() {
        return Optional.ofNullable(title.asComponent()).orElse(ComponentUtility.empty());
    }


    @Override
    public @NotNull VisualkitElement title(@Nullable Component title) {
        Optional.ofNullable(this.title).ifPresent(Xmpl::kill);
        this.title = title != null ? new Xmpl(title) : null;
        return this;
    }

    private @Nullable Lore lore = null;

    @Override
    public @NotNull List<Component> lore() {
        return Optional.ofNullable(lore).orElse(Lore.empty()).asComponentList();
    }

    @Override
    public @NotNull VisualkitElement lore(@NotNull Component... components) {
        if (lore != null) {
            lore.kill();
        }

        lore = new Lore(components);
        return this;
    }

    private int stack = 1;

    @Override
    public int stack() {
        return stack;
    }

    @Override
    public @NotNull VisualkitElement stack(int stack) {
        this.stack = Math.max(Math.min(stack, this.type.getMaxStackSize()), 1);
        return this;
    }

    private int model = -1;

    @Override
    public int model() {
        return model;
    }

    @Override
    public @NotNull VisualkitElement model(int model) {
        this.model = model;
        return this;
    }

    private boolean aura = false;

    @Override
    public boolean aura() {
        return aura;
    }

    @Override
    public @NotNull VisualkitElement aura(boolean aura) {
        this.aura = aura;
        return this;
    }

    private @Nullable Handler handler = null;

    @Override
    public @Nullable Handler handler() {
        return handler;
    }

    @Override
    public @NotNull VisualkitElement handler(@NotNull Handler1 handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public @NotNull VisualkitElement handler(@NotNull Handler2 handler) {
        this.handler = handler;
        return this;
    }

    private @Nullable Sound sound;
    private float volume = 1.0f;
    private float pitch = 1.0f;

    @Override
    public @Nullable Sound sound() {
        return sound;
    }

    @Override
    public float volume() {
        return volume;
    }

    @Override
    public float pitch() {
        return pitch;
    }

    @Override
    public @NotNull VisualkitElement sound(@NotNull Sound sound) {
        return sound(sound, 1.0f, 1.0f);
    }

    @Override
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
     * Build an {@link ItemStack}
     *
     * @param target Xmpl target
     * @return ItemStack
     */
    public @NotNull ItemStack asItemStack(@NotNull XmplTarget target) {
        ItemStack item = this.item.getType() != type ? (this.item = new ItemStack(type, stack)) : this.item;
        item.setAmount(stack);

        ItemMeta meta = item.getItemMeta();

        Optional.ofNullable(title).ifPresent(title -> {
            title.setTarget(target);
            meta.displayName(title.asComponent());
        });

        Optional.ofNullable(lore).ifPresent(lore -> {
            lore.setTarget(target);
            meta.lore(lore.asComponentList());
        });

        meta.setCustomModelData(0 <= model ? model : null);
        meta.addItemFlags(ItemFlag.values());
        meta.removeEnchantments();

        meta.setHideTooltip((meta.displayName() == null && ! meta.hasLore()) || meta.isHideTooltip());

        Visualkit.adapter().fun_adp3uc(item, meta, Visualkit.plugin());

        if (aura) {
            meta.addEnchant(Enchantment.INFINITY, 1, false);
        }

        item.setItemMeta(meta);
        return item;
    }
}
