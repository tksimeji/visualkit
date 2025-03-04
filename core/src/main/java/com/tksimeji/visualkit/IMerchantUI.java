package com.tksimeji.visualkit;

import com.tksimeji.visualkit.trade.VisualkitTrade;
import org.bukkit.inventory.MerchantInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Deprecated(since = "1.0.0", forRemoval = true)
public interface IMerchantUI extends IContainerUI<MerchantInventory> {
    /**
     * Gets any trade.
     *
     * @param index Trade list position
     * @return Trade
     */
    @Nullable VisualkitTrade getTrade(int index);

    /**
     * Gets trade list.
     *
     * @return Trade list
     */
    @NotNull List<VisualkitTrade> getTrades();

    /**
     * Sets any trade.
     *
     * @param index Trade list position
     * @param trade Trade
     */
    void setTrade(int index, @NotNull VisualkitTrade trade);

    /**
     * Add any trade.
     *
     * @param trade Trade
     */
    void addTrade(@NotNull VisualkitTrade trade);

    /**
     * Remove any trade.
     *
     * @param index Trade list position
     */
    void removeTrade(int index);

    /**
     * Remove any trade.
     *
     * @param trade Trade
     */
    void removeTrade(@NotNull VisualkitTrade trade);

    /**
     * Determine if the trade list is empty.
     *
     * @return True if there are no trades
     */
    boolean isEmpty();

    /**
     * Called when a trade is selected.
     *
     * @param index Trade list position
     */
    boolean onSelected(int index);

    /**
     * Called when a trade is purchased.
     *
     * @param trade Trade
     */
    boolean onPurchase(@NotNull VisualkitTrade trade);

    /**
     * Delete all trade
     */
    void clear();

    /**
     * Gets the size of the trade list.
     *
     * @return Trade list size
     */
    int size();
}
