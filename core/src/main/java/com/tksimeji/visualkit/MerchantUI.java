package com.tksimeji.visualkit;

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
import java.util.*;

@ApiStatus.Experimental
public abstract class MerchantUI extends ContainerUI<MerchantInventory> implements IMerchantUI {

    private @NotNull MerchantInventory inventory;

    private final @NotNull Merchant merchant = Bukkit.createMerchant(title());

    private final @NotNull List<VisualkitTrade> trades = new ArrayList<>();
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
        trades.set(index, trade);
        push();
    }

    @Override
    public final void addTrade(@NotNull VisualkitTrade trade) {
        trades.add(trade);
        trade.addObserver(this);
        push();
    }

    @Override
    public final void removeTrade(int index) {
        Optional.ofNullable(getTrade(index)).ifPresent(trade -> trade.removeObserver(this));
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
    public final void onSelected(int index) {
        Optional.ofNullable(getTrade(index)).flatMap(trade -> Optional.ofNullable(trade.onSelect())).ifPresent(VisualkitTrade.SelectHandler::onSelect);
    }

    @Override
    public final void onPurchase(@NotNull VisualkitTrade trade) {
        Optional.ofNullable(trade.onPurchase()).ifPresent(VisualkitTrade.PurchaseHandler::onPurchase);
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
                            setTrade(Math.max(index, size()), trade);
                            crawledFields.add(field);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
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
