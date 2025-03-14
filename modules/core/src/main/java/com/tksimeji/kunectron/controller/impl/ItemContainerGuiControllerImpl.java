package com.tksimeji.kunectron.controller.impl;

import com.tksimeji.kunectron.IndexGroup;
import com.tksimeji.kunectron.controller.ItemContainerGuiController;
import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.markupextension.MarkupExtensionSupport;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import com.tksimeji.kunectron.policy.Policy;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ItemContainerGuiControllerImpl<I extends Inventory> extends ContainerGuiControllerImpl<I> implements ItemContainerGuiController<I> {
    private final @NotNull Map<Integer, ItemElement> elements = new HashMap<>();

    private final @NotNull Map<Integer, ItemSlotPolicy> policies = new HashMap<>();

    private @NotNull ItemSlotPolicy defaultPolicy;
    private @NotNull ItemSlotPolicy playerDefaultPolicy;

    public ItemContainerGuiControllerImpl(final @NotNull Object gui) {
        this(gui, Policy.itemSlot(false), Policy.itemSlot(false));
    }

    public ItemContainerGuiControllerImpl(final @NotNull Object gui, final @NotNull ItemSlotPolicy defaultPolicy, final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        super(gui);
        this.defaultPolicy = defaultPolicy;
        this.playerDefaultPolicy = playerDefaultPolicy;
    }

    @Override
    public @Nullable ItemElement getElement(final int index) {
        return elements.get(index);
    }

    @Override
    public @NotNull Map<Integer, ItemElement> getElements() {
        return new HashMap<>(elements);
    }

    @Override
    public void setElement(final int index, final @Nullable ItemElement element) {
        ItemStack old = getInventory().getItem(index);
        if (!isValidIndex(index) || (element == null && old == null) || (element != null && element.create(getLocale()).equals(old))) {
            return;
        }

        ItemElement aElement = element != null ? element.createCopy() : null;

        if (aElement instanceof MarkupExtensionSupport markupExtensionSupport) {
            markupExtensionSupport.setContext(markupExtensionContext);
        }

        elements.put(index, aElement);
        getInventory().setItem(index, element != null ? aElement.create(getLocale()) : null);
    }

    @Override
    public @NotNull ItemSlotPolicy getPolicy(final int index) {
        ItemElement element = getElement(index);

        if (element != null) {
            ItemSlotPolicy elementPolicy = element.policy();

            if (elementPolicy != null) {
                return elementPolicy;
            }
        }

        return Optional.ofNullable(policies.get(index)).orElse(index < 0 || isValidIndex(index) ? defaultPolicy : playerDefaultPolicy);
    }

    @Override
    public @NotNull Map<Integer, ItemSlotPolicy> getPolicies() {
        return new HashMap<>(policies.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getPolicy(entry.getKey()))));
    }

    @Override
    public void setPolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        policies.put(index, policy);
    }

    @Override
    public @NotNull ItemSlotPolicy getDefaultPolicy() {
        return defaultPolicy;
    }

    @Override
    public void setDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }

    @Override
    public @NotNull ItemSlotPolicy getPlayerDefaultPolicy() {
        return playerDefaultPolicy;
    }

    @Override
    public void setPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        this.playerDefaultPolicy = playerDefaultPolicy;
    }

    @Override
    public int getSize() {
        return getInventory().getSize();
    }

    @Override
    public @NotNull Locale getLocale() {
        return getPlayer().locale();
    }

    @Override
    public boolean isValidIndex(final int index) {
        return index >= 0 && index < getSize();
    }

    @Override
    public void tick() {
        for (Map.Entry<Integer, ItemElement> entry : getElements().entrySet()) {
            setElement(entry.getKey(), entry.getValue());
        }
    }

    @ApiStatus.Internal
    protected @NotNull Set<Integer> parseIndexGroup(final @NotNull IndexGroup indexGroup, final boolean player) {
        return parseIndexGroup(indexGroup).stream().map(index -> player ? index + getInventory().getSize() : index).collect(Collectors.toSet());
    }

    @ApiStatus.Internal
    protected @NotNull Set<Integer> parseIndexGroup(final int[] value, final @NotNull IndexGroup[] indexGroups, final boolean player) {
        return parseIndexGroup(value, indexGroups).stream().map(index -> player ? index + getInventory().getSize() : index).collect(Collectors.toSet());
    }
}
