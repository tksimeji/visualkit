package com.tksimeji.visualkit.element;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    class Ingredients implements Iterable<ItemStack> {
        private final @NotNull ItemStack ingredient1;
        private final @Nullable ItemStack ingredient2;

        public Ingredients(final @NotNull ItemStack ingredient) {
            this(ingredient, null);
        }

        public Ingredients(final @NotNull ItemStack ingredient1, final @Nullable ItemStack ingredient2) {
            this.ingredient1 = ingredient1;
            this.ingredient2 = ingredient2;
        }

        public @NotNull ItemStack getIngredient1() {
            return ingredient1.clone();
        }

        public @Nullable ItemStack getIngredient2() {
            return ingredient2 != null ? ingredient2.clone() : null;
        }

        public boolean hasIngredient2() {
            return getIngredient2() != null;
        }

        @Override
        public @NotNull Iterator<ItemStack> iterator() {
            List<ItemStack> collection = new ArrayList<>();
            collection.add(getIngredient1());

            if (hasIngredient2()) {
                collection.add(getIngredient2());
            }

            return collection.iterator();
        }
    }
}
