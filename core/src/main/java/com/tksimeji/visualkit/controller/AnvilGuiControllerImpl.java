package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.AnvilGui;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.impl.ItemContainerGuiControllerImpl;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.event.AnvilGuiEvents;
import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.event.anvil.AnvilGuiClickEventImpl;
import com.tksimeji.visualkit.event.anvil.AnvilGuiCloseEventImpl;
import com.tksimeji.visualkit.event.anvil.AnvilGuiInitEventImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class AnvilGuiControllerImpl extends ItemContainerGuiControllerImpl<AnvilInventory> implements AnvilGuiController {
    private final @NotNull Player player;

    private @NotNull AnvilInventory inventory;

    private @NotNull String text = "";

    private final boolean overwriteResultSlot;

    public AnvilGuiControllerImpl(final @NotNull Object gui, final @NotNull AnvilGui annotation) {
        super(gui);

        player = getDeclarationOrThrow(gui, AnvilGui.Player.class, Player.class).getLeft();
        overwriteResultSlot = annotation.overwriteResultSlot();

        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> {
            inventory = Visualkit.adapter().fun_u32qi0(player, getDeclarationOrDefault(gui, AnvilGui.Title.class, ComponentLike.class, Component.empty()).getLeft().asComponent());

            Optional.ofNullable(getDeclaration(gui, AnvilGui.FirstElement.class, ItemElement.class))
                    .ifPresent(firstElement -> setFirstElement(firstElement.getLeft()));

            Optional.ofNullable(getDeclaration(gui, AnvilGui.SecondElement.class, ItemElement.class))
                    .ifPresent(secondElement -> setSecondElement(secondElement.getLeft()));

            Optional.ofNullable(getDeclaration(gui, AnvilGui.ResultElement.class, ItemElement.class))
                    .ifPresent(resultElement -> setResultElement(resultElement.getLeft()));
        });
    }

    @Override
    public void init() {
        callEvent(new AnvilGuiInitEventImpl(gui));
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull AnvilInventory getInventory() {
        return inventory;
    }

    @Override
    public @NotNull String getText() {
        return text;
    }

    @Override
    public @Nullable ItemElement getFirstElement() {
        return getElement(0);
    }

    @Override
    public void setFirstElement(final @Nullable ItemElement element) {
        setElement(0, element);
    }

    @Override
    public @Nullable ItemElement getSecondElement() {
        return getElement(1);
    }

    @Override
    public void setSecondElement(final @Nullable ItemElement element) {
        setElement(1, element);
    }

    @Override
    public @Nullable ItemElement getResultElement() {
        return getElement(2);
    }

    @Override
    public void setResultElement(final @Nullable ItemElement element) {
        setElement(2, element);
    }

    @Override
    public void setElement(int index, @Nullable ItemElement element) {
        ItemStack old = getInventory().getItem(index);
        if ((element == null && old == null) || (element != null && element.create().equals(old))) {
            return;
        }

        super.setElement(index, element);
    }

    @Override
    public boolean isOverwriteResultSlot() {
        return overwriteResultSlot;
    }

    @Override
    public boolean click(int index, @NotNull Action action, @NotNull Mouse mouse) {
        return callEvent(new AnvilGuiClickEventImpl(gui, index, getElement(index), action, mouse));
    }

    @Override
    public void close() {
        callEvent(new AnvilGuiCloseEventImpl(gui, text));
        inventory.clear();
        super.close();
    }

    @Override
    public boolean callEvent(@NotNull Event event) {
        if (event instanceof AnvilGuiEvents.TextChangeEvent textChangeEvent) {
            text = textChangeEvent.getText();
        }

        return super.callEvent(event);
    }
}
