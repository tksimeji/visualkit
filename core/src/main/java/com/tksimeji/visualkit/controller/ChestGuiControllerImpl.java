package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.ChestGui;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.event.ChestGuiEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

public final class ChestGuiControllerImpl extends InventoryGuiControllerImpl implements ChestGuiController {
    private static @NotNull Set<Integer> parseAnnotation(final @NotNull ChestGui.Element annotation) {
        return parseAnnotation(annotation.value(), annotation.groups());
    }

    private static @NotNull Set<Integer> parseAnnotation(final @NotNull ChestGui.IndexGroup annotation) {
        HashSet<Integer> indexes = new HashSet<>(Arrays.stream(annotation.indexes()).boxed().toList());

        if (annotation.indexFrom() >= 0 && annotation.indexFrom() <= annotation.indexTo()) {
            indexes.addAll(IntStream.rangeClosed(annotation.indexFrom(), annotation.indexTo()).boxed().toList());
        }

        for (int expectIndex : annotation.expectIndexes()) {
            indexes.remove(expectIndex);
        }

        return indexes;
    }

    private static @NotNull Set<Integer> parseAnnotation(final int[] value, @NotNull ChestGui.IndexGroup[] groups) {
        Set<Integer> indexes = new HashSet<>(Arrays.stream(value).boxed().toList());

        for (ChestGui.IndexGroup indexGroup : groups) {
            indexes.addAll(parseAnnotation(indexGroup));
        }

        return indexes;
    }

    private final @NotNull Player player;

    private final @NotNull Inventory inventory;

    private final @NotNull Map<Integer, ItemElement> elementMap = new HashMap<>();

    public ChestGuiControllerImpl(final @NotNull Object gui) {
        super(gui);

        inventory = Bukkit.createInventory(null,
                getDeclarationOrDefault(gui, ChestGui.Size.class, ChestGui.ChestSize.class, ChestGui.ChestSize.SIZE_54).getLeft().toInt(),
                getDeclarationOrDefault(gui, ChestGui.Title.class, ComponentLike.class, Component.empty()).getLeft().asComponent());

        player = getDeclarationOrThrow(gui, ChestGui.Player.class, Player.class).getLeft();

        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> player.openInventory(inventory));

        for (Pair<ItemElement, ChestGui.Element> pair : getDeclarations(gui, ChestGui.Element.class, ItemElement.class)) {
            for (int index : parseAnnotation(pair.getRight())) {
                setElement(index, pair.getLeft());
            }
        }

        callEvent(new ChestGuiEvents.InitEvent(gui));
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull ItemElement getElement(final int index) {
        return elementMap.get(index);
    }

    @Override
    public @NotNull Map<Integer, ItemElement> getElements() {
        return new HashMap<>(elementMap);
    }

    @Override
    public void setElement(final int index, final @Nullable ItemElement element) {
        ItemStack old = inventory.getItem(index);
        if ((element == null && old == null) || (element != null && element.create() == old)) {
            return;
        }

        elementMap.put(index, element);
        inventory.setItem(index, element != null ? element.create() : null);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean click(final int index, final @NotNull Action action, final @NotNull Mouse mouse) {
        return callEvent(new ChestGuiEvents.ClickEvent(gui, index, getElement(index), action, mouse));
    }

    @Override
    public void close() {
        callEvent(new ChestGuiEvents.CloseEvent(gui));
        super.close();
    }

    @Override
    public void tick() {
        for (Map.Entry<Integer, ItemElement> entry : elementMap.entrySet()) {
            setElement(entry.getKey(), entry.getValue());
        }
    }
}
