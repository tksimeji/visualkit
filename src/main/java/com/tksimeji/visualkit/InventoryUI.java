package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.*;
import com.tksimeji.visualkit.element.VisualkitElement;
import com.tksimeji.visualkit.policy.PolicyTarget;
import com.tksimeji.visualkit.policy.SlotPolicy;
import com.tksimeji.visualkit.util.KillableHashMap;
import com.tksimeji.visualkit.util.AsmUtility;
import com.tksimeji.visualkit.util.ReflectionUtility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class InventoryUI<I extends Inventory> extends VisualkitUI implements IInventoryUI<I> {
    protected final @NotNull Player player;

    protected final @NotNull Map<Integer, VisualkitElement> elements = new KillableHashMap<>();
    protected final @NotNull Map<Integer, SlotPolicy> policies = new HashMap<>();
    protected final @NotNull Set<Method> handlers = ReflectionUtility.getMethods(getClass()).stream()
            .filter(method -> method.isAnnotationPresent(Handler.class))
            .collect(Collectors.toSet());

    private final @NotNull Set<Field> crawledFields = new HashSet<>();

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
        setElement(slot, element != null ? VisualkitElement.of(element) : null);
    }

    @Override
    public @NotNull SlotPolicy getPolicy(int slot) {
        return getPolicy(slot, PolicyTarget.UI);
    }

    @Override
    public @NotNull SlotPolicy getPolicy(int slot, @NotNull PolicyTarget target) {
        if (target == PolicyTarget.INVENTORY) {
            slot += asInventory().getSize();
        }

        return Optional.ofNullable(policies.get(slot)).orElse(SlotPolicy.FIXATION);
    }

    @Override
    public void setPolicy(int slot, @NotNull SlotPolicy policy) {
        setPolicy(slot, policy, PolicyTarget.UI);
    }

    @Override
    public void setPolicy(int slot, @NotNull SlotPolicy policy, @NotNull PolicyTarget target) {
        if (target == PolicyTarget.INVENTORY) {
            slot += asInventory().getSize();
        }

        if (slot < 0 || asInventory().getSize() + player.getOpenInventory().getBottomInventory().getSize() <= slot) {
            return;
        }

        policies.put(slot, policy);
    }

    @Override
    public final void onClick(int slot, @NotNull Click click, @NotNull Mouse mouse) {
        elements.entrySet().stream()
                .filter(entry -> entry.getKey() == slot)
                .forEach(entry -> {
                    VisualkitElement element = entry.getValue();
                    Optional.ofNullable(element.handler()).ifPresent(handler -> handler.onClick(slot, click, mouse));
                    Optional.ofNullable(element.sound()).ifPresent(sound -> player.playSound(player, sound, element.volume(), element.pitch()));
                });

        handlers.stream()
                .filter(handler -> {
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

                        if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
                            args[i] = slot;
                        } else if (Click.class.isAssignableFrom(type)) {
                            args[i] = click;
                        } else if (Mouse.class.isAssignableFrom(type)) {
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

        if (! player.getOpenInventory().getTopInventory().isEmpty() &&
                Visualkit.sessions(InventoryUI.class).stream().noneMatch(s -> s.getPlayer() == player)) {
            player.closeInventory();
        }

        player.updateInventory();
        elements.clear();
    }

    @Override
    public final void tick() {
        onTick();

        elements.forEach(this::setElement);

        ReflectionUtility.getFields(getClass()).stream()
                .filter(field -> ! crawledFields.contains(field) &&
                        field.isAnnotationPresent(Element.class) &&
                        (VisualkitElement.class.isAssignableFrom(field.getType()) || ItemStack.class.isAssignableFrom(field.getType())))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        Object element = field.get(InventoryUI.this);

                        AsmUtility.of(field.getAnnotation(Element.class)).forEach(slot -> {
                            if (element instanceof VisualkitElement e) {
                                setElement(slot, e);
                            } else if (element instanceof ItemStack e) {
                                setElement(slot, e);
                            } else if (element != null) {
                                throw new UnsupportedOperationException("Unsupported element class: " + element.getClass().getName());
                            }

                            if (element != null) {
                                crawledFields.add(field);
                            }
                        });
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        ReflectionUtility.getFields(getClass()).stream()
                .filter(field -> ! crawledFields.contains(field) &&
                        field.isAnnotationPresent(Policy.class) &&
                        field.getType() == SlotPolicy.class)
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    Policy annotation = field.getAnnotation(Policy.class);

                    try {
                        SlotPolicy policy = (SlotPolicy) field.get(InventoryUI.this);

                        if (policy != null) {
                            AsmUtility.of(annotation).forEach(slot -> setPolicy(slot, policy, annotation.target()));
                            crawledFields.add(field);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
