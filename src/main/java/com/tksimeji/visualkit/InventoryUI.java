package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.*;
import com.tksimeji.visualkit.element.VisualkitElement;
import com.tksimeji.visualkit.util.KillableHashMap;
import com.tksimeji.visualkit.util.AsmUtility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class InventoryUI<I extends Inventory> implements IInventoryUI<I> {
    protected final Player player;

    protected final Map<Integer, VisualkitElement> elements = new KillableHashMap<>();
    protected final Set<Method> handlers = Arrays.stream(getClass().getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(Handler.class) && method.getParameters().length == 0)
            .collect(Collectors.toSet());

    public InventoryUI(@NotNull Player player) {
        this.player = player;
        Visualkit.sessions.add(this);
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @Nullable VisualkitElement getElement(int slot) {
        return elements.get(slot);
    }

    @Override
    public void setElement(int slot, @Nullable VisualkitElement element) {
        if (slot < 0 || asInventory().getSize() <= slot) {
            return;
        }

        if (element == null) {
            elements.remove(slot);
            asInventory().clear(slot);
            return;
        }

        elements.put(slot, element);

        ItemStack item = element.asItemStack(this);

        if (item.equals(asInventory().getItem(slot))) {
            return;
        }

        asInventory().setItem(slot, item);
    }

    @Override
    public final void onClick(int slot, @NotNull Click click, @NotNull Mouse mouse) {
        handlers.stream().filter(handler -> {
            Handler annotation = handler.getAnnotation(Handler.class);
            return AsmUtility.of(annotation).stream().anyMatch(s -> s == slot) &&
                    Arrays.stream(annotation.click()).toList().contains(click) &&
                    Arrays.stream(annotation.mouse()).toList().contains(mouse);
        }).forEach(handler -> {
           try {
               handler.setAccessible(true);
               handler.invoke(this);
           } catch (InvocationTargetException | IllegalAccessException e) {
               throw new RuntimeException(e);
           }
        });
    }

    @Override
    public final void close() {
        onClose();

        Visualkit.sessions.remove(this);

        if (! player.getOpenInventory().getTopInventory().isEmpty()) {
            player.closeInventory();
        }

        player.updateInventory();
        elements.clear();
    }

    @Override
    public final void tick() {
        onTick();

        Arrays.stream(getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InitialElement.class) && field.getType() == VisualkitElement.class)
                .forEach(field -> {
                    field.setAccessible(true);

                    try {
                        VisualkitElement element = (VisualkitElement) field.get(InventoryUI.this);

                        AsmUtility.of(field.getAnnotation(InitialElement.class)).forEach(slot -> {
                            setElement(slot, element);
                        });
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
