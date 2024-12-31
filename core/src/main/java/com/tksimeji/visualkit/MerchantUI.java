package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Action;
import com.tksimeji.visualkit.api.Handler;
import com.tksimeji.visualkit.api.Trade;
import com.tksimeji.visualkit.trade.VisualkitTrade;
import com.tksimeji.visualkit.util.ReflectionUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

@ApiStatus.Experimental
public abstract class MerchantUI extends ContainerUI<MerchantInventory> implements IMerchantUI {

    private @NotNull MerchantInventory inventory;

    private final @NotNull Merchant merchant = Bukkit.createMerchant(title());

    private final @NotNull List<VisualkitTrade> trades = new ArrayList<>();

    private final @NotNull Set<Method> handlers = ReflectionUtility.getMethods(getClass()).stream()
            .filter(method -> method.isAnnotationPresent(Handler.class) &&
                    (method.getReturnType() == Void.class || method.getReturnType() == Boolean.class || method.getReturnType() == boolean.class))
            .collect(Collectors.toSet());

    private final @NotNull Set<Field> crawledFields = new HashSet<>();

    private boolean pushed;

    public MerchantUI(@NotNull Player player) {
        super(player);
        push();
    }

    @Override
    public final @Nullable VisualkitTrade getTrade(int index) {
        return trades.get(index);
    }

    @Override
    public final @NotNull List<VisualkitTrade> getTrades() {
        return new ArrayList<>(trades);
    }

    @Override
    public final void setTrade(int index, @NotNull VisualkitTrade trade) {
        if (index < 0 || size() < index) {
            throw new IllegalArgumentException();
        }

        if (index == size()) {
            trades.add(null);
        }

        trade.addObserver(this);
        trades.set(index, trade);
        push();
    }

    @Override
    public final void addTrade(@NotNull VisualkitTrade trade) {
        setTrade(size(), trade);
    }

    @Override
    public final void removeTrade(int index) {
        if (index < 0 || size() <= index) {
            return;
        }

        Objects.requireNonNull(getTrade(index)).removeObserver(this);
        trades.remove(index);
        push();
    }

    @Override
    public final void removeTrade(@NotNull VisualkitTrade trade) {
        removeTrade(trades.indexOf(trade));
    }

    @Override
    public final @NotNull MerchantInventory asInventory() {
        return inventory;
    }

    @Override
    public final boolean isEmpty() {
        return trades.isEmpty();
    }

    @Override
    public final boolean onSelected(int index) {
        VisualkitTrade trade = getTrade(index);

        if (trade == null) {
            return false;
        }

        Optional.ofNullable(trade.onSelect()).ifPresent(VisualkitTrade.SelectHandler::onSelect);

        return handlers.stream()
                .filter(handler -> {
                    Handler annotation = handler.getAnnotation(Handler.class);
                    return Arrays.stream(annotation.action()).anyMatch(action -> action == Action.SELECT) &&
                        (annotation.slot().length == 0 || Arrays.stream(annotation.slot()).anyMatch(slot -> slot == index));
                }).allMatch(handler -> {
                    Parameter[] parameters = handler.getParameters();
                    Object[] args = new Object[parameters.length];

                    for (int i = 0; i < args.length; i ++) {
                        Parameter parameter = parameters[i];
                        Class<?> type = parameter.getType();

                        if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
                            args[i] = index;
                        } else if (VisualkitTrade.class.isAssignableFrom(type)) {
                            args[i] = trade;
                        } else {
                            args[i] = null;
                        }
                    }

                    try {
                        handler.setAccessible(true);
                        Object result = handler.invoke(this, args);
                        return result == null || ((boolean) result);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public final boolean onPurchase(@NotNull VisualkitTrade trade) {
        Optional.ofNullable(trade.onPurchase()).ifPresent(VisualkitTrade.PurchaseHandler::onPurchase);

        int index = trades.indexOf(trade);

        return handlers.stream()
                .filter(handler -> {
                    Handler annotation = handler.getAnnotation(Handler.class);
                    return Arrays.stream(annotation.action()).anyMatch(action -> action == Action.PURCHASE) &&
                            (annotation.slot().length == 0 || Arrays.stream(annotation.slot()).anyMatch(slot -> slot == index));
                }).allMatch(handler -> {
                    Parameter[] parameters = handler.getParameters();
                    Object[] args = new Object[parameters.length];

                    for (int i = 0; i < args.length; i ++) {
                        Parameter parameter = parameters[i];
                        Class<?> type = parameter.getType();

                        if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
                            args[i] = index;
                        } else if (VisualkitTrade.class.isAssignableFrom(type)) {
                            args[i] = trade;
                        } else {
                            args[i] = null;
                        }
                    }

                    try {
                        handler.setAccessible(true);
                        Object result = handler.invoke(this, args);
                        return result == null || ((boolean) result);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public final void push() {
        merchant.setRecipes(trades.stream().map(VisualkitTrade::asRecipe).toList());
        pushed = true;
        inventory = (MerchantInventory) Objects.requireNonNull(player.openMerchant(merchant, true)).getTopInventory();
    }

    @Override
    public final void clear() {
        trades.clear();
        push();
    }

    @Override
    public final int size() {
        return trades.size();
    }

    @Override
    public final void tick() {
        Map<Integer, VisualkitTrade> queue = new HashMap<>();

        ReflectionUtility.getFields(getClass()).stream()
                .filter(field -> ! crawledFields.contains(field) &&
                        field.isAnnotationPresent(Trade.class) &&
                        VisualkitTrade.class.isAssignableFrom(field.getType()))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        Trade annotation = field.getAnnotation(Trade.class);
                        int index = annotation.index();

                        if (field.get(MerchantUI.this) instanceof VisualkitTrade trade) {
                            if (index < 0) {
                                addTrade(trade);
                            } else {
                                queue.put(index, trade);
                            }

                            crawledFields.add(field);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        queue.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> setTrade(entry.getKey(), entry.getValue()));
    }

    @Override
    public final void close() {
        if (pushed) {
            pushed = false;
            return;
        }

        trades.forEach(trade -> trade.removeObserver(this));
        super.close();
    }
}
