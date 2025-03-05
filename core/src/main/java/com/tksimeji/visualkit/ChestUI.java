package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.*;
import com.tksimeji.visualkit.element.IVisualkitElement;
import com.tksimeji.visualkit.element.ItemStackElement;
import com.tksimeji.visualkit.element.VisualkitElement;
import com.tksimeji.visualkit.event.GuiHandler;
import com.tksimeji.visualkit.policy.PolicyTarget;
import com.tksimeji.visualkit.policy.SlotPolicy;
import com.tksimeji.visualkit.util.AsmUtility;
import com.tksimeji.visualkit.util.KillableHashMap;
import com.tksimeji.visualkit.util.ReflectionUtility;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
public abstract class ChestUI extends ContainerUI<Inventory> implements IChestUI {
    protected final @NotNull Inventory inventory;

    protected final @NotNull Map<Integer, IVisualkitElement<?>> elements = new KillableHashMap<>();
    protected final @NotNull Map<Integer, SlotPolicy> policies = new HashMap<>();
    protected final @NotNull Set<Method> handlers = ReflectionUtility.getMethods(getClass()).stream()
            .filter(method -> method.isAnnotationPresent(GuiHandler.class) &&
                    (List.of(Void.TYPE, Boolean.class, boolean.class).contains(method.getReturnType())))
            .collect(Collectors.toSet());

    private final @NotNull Set<Field> crawledFields = new HashSet<>();

    public ChestUI(@NotNull Player player) {
        super(player);
        inventory = Bukkit.createInventory(null, size().asInt(), title());
        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> player.openInventory(inventory));
    }

    @Override
    public final @Nullable IVisualkitElement<?> getElement(int slot) {
        return elements.get(slot);
    }

    @Override
    public final @NotNull List<IVisualkitElement<?>> getElements() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public final @NotNull Map<Integer, IVisualkitElement<?>> getElementMap() {
        return elements.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public final void setElement(int slot, @Nullable IVisualkitElement<?> element) {
        if (slot < 0 || getSize() <= slot) {
            return;
        }

        if (element == null) {
            elements.remove(slot);
            asInventory().clear(slot);
            return;
        }

        elements.put(slot, element);

        ItemStack item = null;

        if (element instanceof VisualkitElement ve) {
            item = ve.asItemStack(this);
        } else if (element instanceof ItemStackElement is) {
            item = is.asItemStack();
        }

        ItemStack old = asInventory().getItem(slot);

        if (item != null && item.equals(old)) {
            return;
        }

        asInventory().setItem(slot, item);
    }

    @Override
    public void setElement(int slot, @Nullable ItemStack element) {
        setElement(slot, element != null ? VisualkitElement.item(element) : null);
    }

    @Override
    public @NotNull SlotPolicy getPolicy(int slot) {
        return getPolicy(slot, PolicyTarget.UI);
    }

    @Override
    public @NotNull SlotPolicy getPolicy(int slot, @NotNull PolicyTarget target) {
        if (target == PolicyTarget.INVENTORY) {
            slot += getSize();
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
            slot += getSize();
        }

        if (slot < -1 || getSize() + player.getOpenInventory().getBottomInventory().getSize() <= slot) {
            return;
        }

        policies.put(slot, policy);
    }

    @Override
    public final @NotNull Inventory asInventory() {
        return inventory;
    }

    public final boolean onClick(int slot, @NotNull Action action, @NotNull Mouse mouse, @Nullable ItemStack item) {
        throw new NotImplementedException();
    }

    @Override
    public final void close() {
        super.close();
    }

    @Override
    public final void tick() {
        super.tick();

        getElementMap().forEach(this::setElement);

        ReflectionUtility.getFields(getClass()).stream()
                .filter(field -> ! crawledFields.contains(field) &&
                        field.isAnnotationPresent(Element.class) &&
                        (IVisualkitElement.class.isAssignableFrom(field.getType()) || ItemStack.class.isAssignableFrom(field.getType())))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        Object element = field.get(ChestUI.this);

                        AsmUtility.of(field.getAnnotation(Element.class)).forEach(slot -> {
                            if (element instanceof IVisualkitElement<?> e) {
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
                        SlotPolicy policy = (SlotPolicy) field.get(ChestUI.this);

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
