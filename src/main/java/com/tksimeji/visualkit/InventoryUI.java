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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class InventoryUI<I extends Inventory> implements IInventoryUI<I> {
    protected final @NotNull Player player;

    protected final @NotNull Map<Integer, VisualkitElement> elements = new KillableHashMap<>();
    protected final @NotNull Set<Method> handlers = Arrays.stream(getClass().getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(Handler.class))
            .collect(Collectors.toSet());

    private final @NotNull Set<Field> placed = new HashSet<>();

    /**
     * Creating a GUI.
     *
     * @param player Player showing GUI
     */
    public InventoryUI(@NotNull Player player) {
        this.player = player;
        Visualkit.sessions.add(this);
    }

    @Override
    public final @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public final @Nullable VisualkitElement getElement(int slot) {
        return elements.get(slot);
    }

    @Override
    public final void setElement(int slot, @Nullable VisualkitElement element) {
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
        ItemStack old = asInventory().getItem(slot);

        if (item.equals(old)) {
            return;
        }

        asInventory().setItem(slot, item);
    }

    @Override
    public void setElement(int slot, @Nullable ItemStack element) {
        setElement(slot, VisualkitElement.of(element));
    }

    @Override
    public final void onClick(int slot, @NotNull Click click, @NotNull Mouse mouse) {
        handlers.stream().filter(handler -> {
            Handler annotation = handler.getAnnotation(Handler.class);
            return AsmUtility.of(annotation).stream().anyMatch(s -> s == slot) &&
                    Arrays.stream(annotation.click()).toList().contains(click) &&
                    Arrays.stream(annotation.mouse()).toList().contains(mouse);
        }).forEach(handler -> {
            Parameter[] parameters = handler.getParameters();
            Object[] args = new Object[parameters.length];

            for (int i = 0; i < args.length; i++) {
                Parameter parameter = parameters[i];
                Class<?> type = parameter.getType();

                if (parameter.getName().equals("$slot") && (Integer.class.isAssignableFrom(type)) || int.class.isAssignableFrom(type)) {
                    args[i] = slot;
                } else if (parameter.getName().equals("$click") && Click.class.isAssignableFrom(type)) {
                    args[i] = click;
                } else if (parameter.getName().equals("$mouse") && Mouse.class.isAssignableFrom(type)) {
                    args[i] = mouse;
                } else {
                    args[i] = null;
                }
            }

            try {
                handler.setAccessible(true);
                handler.invoke(this, args);
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

        elements.forEach(this::setElement);

        Arrays.stream(getClass().getDeclaredFields())
                .filter(field -> ! placed.contains(field) &&
                        field.isAnnotationPresent(Element.class) &&
                        (VisualkitElement.class.isAssignableFrom(field.getType()) || ItemStack.class.isAssignableFrom(field.getType())))
                .forEach(field -> {
                    field.setAccessible(true);

                    try {
                        Object element = field.get(InventoryUI.this);

                        AsmUtility.of(field.getAnnotation(Element.class)).forEach(slot -> {
                            if (element instanceof VisualkitElement e) {
                                setElement(slot, e);
                            } else if (element instanceof ItemStack e) {
                                setElement(slot, e);
                            } else {
                                throw new UnsupportedOperationException();
                            }

                            placed.add(field);
                        });
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
