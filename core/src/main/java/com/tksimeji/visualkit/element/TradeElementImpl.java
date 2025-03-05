package com.tksimeji.visualkit.element;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

class TradeElementImpl implements TradeElement {
    private @NotNull ItemStack result;

    private @NotNull Ingredients ingredients;

    private int maxUses = Integer.MAX_VALUE;

    public TradeElementImpl(final @NotNull ItemStack result, final @NotNull ItemStack ingredient) {
        this(result, ingredient, null);
    }

    public TradeElementImpl(final @NotNull ItemStack result, final @NotNull ItemStack ingredient1, final @Nullable ItemStack ingredient2) {
        this.result = result;
        ingredients = new Ingredients(ingredient1.clone(), ingredient2 != null ? ingredient2.clone() : null);
    }

    @Override
    public @NotNull ItemStack result() {
        return result;
    }

    @Override
    public @NotNull TradeElement result(final @NotNull ItemStack result) {
        this.result = result;
        return this;
    }

    @Override
    public @NotNull Ingredients ingredients() {
        return ingredients;
    }

    @NotNull
    @Override
    public TradeElement ingredient(@NotNull ItemStack ingredient) {
        return ingredients(ingredient, null);
    }

    @NotNull
    @Override
    public TradeElement ingredients(@NotNull ItemStack ingredient1, @Nullable ItemStack ingredient2) {
        this.ingredients = new Ingredients(ingredient1, ingredient2);
        return this;
    }

    @Override
    public int maxUses() {
        return maxUses;
    }

    @Override
    public @NotNull TradeElement maxUses(final @Range(from = 0, to = Integer.MAX_VALUE) int maxUses) {
        this.maxUses = maxUses;
        return this;
    }

    @ApiStatus.Internal
    @Override
    public @NotNull MerchantRecipe create() {
        MerchantRecipe merchantRecipe = new MerchantRecipe(result, maxUses);

        for (ItemStack ingredient : ingredients) {
            merchantRecipe.addIngredient(ingredient);
        }

        return merchantRecipe;
    }
}
