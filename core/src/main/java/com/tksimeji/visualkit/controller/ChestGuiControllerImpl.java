package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.ChestGui;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.impl.ItemContainerGuiControllerImpl;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.event.chest.ChestGuiClickEventImpl;
import com.tksimeji.visualkit.event.chest.ChestGuiCloseEventImpl;
import com.tksimeji.visualkit.event.chest.ChestGuiInitEventImpl;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class ChestGuiControllerImpl extends ItemContainerGuiControllerImpl<Inventory> implements ChestGuiController {
    private final @NotNull Player player;

    private final @NotNull Inventory inventory;

    public ChestGuiControllerImpl(final @NotNull Object gui) {
        super(gui);

        inventory = Bukkit.createInventory(null,
                getDeclarationOrDefault(gui, ChestGui.Size.class, ChestGui.ChestSize.class, ChestGui.ChestSize.SIZE_54).getLeft().toInt(),
                getDeclarationOrDefault(gui, ChestGui.Title.class, ComponentLike.class, Component.empty()).getLeft().asComponent());

        player = getDeclarationOrThrow(gui, ChestGui.Player.class, Player.class).getLeft();

        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> player.openInventory(inventory));

        getDeclaration(gui, ChestGui.DefaultPolicy.class, ItemSlotPolicy.class).ifPresent(declaration -> {
            setDefaultPolicy(declaration.getLeft());
        });

        getDeclaration(gui, ChestGui.PlayerDefaultPolicy.class, ItemSlotPolicy.class).ifPresent(declaration -> {
            setPlayerDefaultPolicy(declaration.getLeft());
        });

        for (Pair<ItemElement, ChestGui.Element> declaration : getDeclarations(gui, ChestGui.Element.class, ItemElement.class)) {
            for (int index : parseAnnotation(declaration.getRight())) {
                setElement(index, declaration.getLeft());
            }
        }

        for (Pair<ItemSlotPolicy, ChestGui.Policy> declaration : getDeclarations(gui, ChestGui.Policy.class, ItemSlotPolicy.class)) {
            for (int index : parseAnnotation(declaration.getRight())) {
                setPolicy(index, declaration.getLeft());
            }
        }
    }

    @Override
    public void init() {
        callEvent(new ChestGuiInitEventImpl(gui));
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public void click(final int index, final @NotNull Action action, final @NotNull Mouse mouse) {
        callEvent(new ChestGuiClickEventImpl(gui, index, getElement(index), action, mouse));
    }

    @Override
    public void close() {
        callEvent(new ChestGuiCloseEventImpl(gui));
        super.close();
    }

    @ApiStatus.Internal
    private @NotNull Set<Integer> parseAnnotation(final @NotNull ChestGui.Element annotation) {
        return parseIndexGroup(annotation.value(), annotation.groups());
    }

    @ApiStatus.Internal
    private @NotNull Set<Integer> parseAnnotation(final @NotNull ChestGui.Policy annotation) {
        return parseIndexGroup(annotation.value(), annotation.groups(), annotation.player());
    }
}
