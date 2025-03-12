package com.tksimeji.kunectron.element;

import com.tksimeji.kunectron.event.MerchantGuiEvents;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public interface TradeElement extends Element<MerchantRecipe> {
    @NotNull ItemStack result();

    @Contract("_ -> this")
    @NotNull TradeElement result(final @NotNull ItemStack result);

    @NotNull Ingredients ingredients();

    @Contract("_ -> this")
    @NotNull TradeElement ingredient(final @NotNull ItemStack ingredient);

    @Contract("_, _ -> this")
    @NotNull TradeElement ingredients(final @NotNull ItemStack ingredient1, final @Nullable ItemStack ingredient2);

    int maxUses();

    @Contract("_ -> this")
    @NotNull TradeElement maxUses(final @Range(from = 0, to = Integer.MAX_VALUE) int maxUses);

    boolean canSelect();

    @Contract("_ -> this")
    @NotNull TradeElement canSelect(final boolean canSelect);

    boolean canPurchase();

    @Contract("_ -> this")
    @NotNull TradeElement canPurchase(final boolean canUse);

    @Nullable TradeElement.SelectHandler selectHandler();

    @Contract("_ -> this")
    @NotNull TradeElement selectHandler(final @NotNull TradeElement.SelectHandler1 handler);

    @Contract("_ -> this")
    @NotNull TradeElement selectHandler(final @NotNull TradeElement.SelectHandler2 handler);

    @Nullable TradeElement.PurchaseHandler purchaseHandler();

    @Contract("_ -> this")
    @NotNull TradeElement purchaseHandler(final @NotNull TradeElement.PurchaseHandler1 handler);

    @Contract("_ -> this")
    @NotNull TradeElement purchaseHandler(final @NotNull TradeElement.PurchaseHandler2 handler);

    @Override
    @NotNull TradeElement createCopy();

    boolean equals(final @Nullable MerchantRecipe object);

    interface SelectHandler {
    }

    interface SelectHandler1 extends SelectHandler {
        void onSelect();
    }

    interface SelectHandler2 extends SelectHandler {
        void onSelect(@NotNull MerchantGuiEvents.SelectEvent event);
    }

    interface PurchaseHandler {
    }

    interface PurchaseHandler1 extends PurchaseHandler {
        void onPurchase();
    }

    interface PurchaseHandler2 extends PurchaseHandler {
        void onPurchase(@NotNull MerchantGuiEvents.PurchaseEvent event);
    }

    interface Ingredients extends Iterable<ItemStack> {
        @NotNull ItemStack getIngredient1();

        @Nullable ItemStack getIngredient2();

        boolean hasIngredient2();
    }
}
