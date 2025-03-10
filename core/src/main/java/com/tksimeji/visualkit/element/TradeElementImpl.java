package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.controller.MerchantGuiController;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashSet;
import java.util.Set;

public class TradeElementImpl implements TradeElement {
    private @NotNull ItemStack result;

    private @NotNull Ingredients ingredients;

    private int maxUses = Integer.MAX_VALUE;

    private boolean canSelect = true;
    private boolean canPurchase = true;

    private @Nullable SelectHandler selectHandler;

    private @Nullable PurchaseHandler purchaseHandler;

    private final @NotNull Set<MerchantGuiController> observers = new HashSet<>();

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
        observers.forEach(MerchantGuiController::update);
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
        observers.forEach(MerchantGuiController::update);
        return this;
    }

    @Override
    public int maxUses() {
        return maxUses;
    }

    @Override
    public @NotNull TradeElement maxUses(final @Range(from = 0, to = Integer.MAX_VALUE) int maxUses) {
        this.maxUses = maxUses;
        observers.forEach(MerchantGuiController::update);
        return this;
    }

    @Override
    public boolean canSelect() {
        return canSelect;
    }

    @Override
    public @NotNull TradeElement canSelect(final boolean canSelect) {
        this.canSelect = canSelect;
        return this;
    }

    @Override
    public boolean canPurchase() {
        return canPurchase;
    }

    @Override
    public @NotNull TradeElement canPurchase(final boolean canPurchase) {
        this.canPurchase = canPurchase;
        return this;
    }

    @Override
    public @Nullable SelectHandler selectHandler() {
        return selectHandler;
    }

    @Override
    public @NotNull TradeElement selectHandler(final @NotNull SelectHandler handler) {
        selectHandler = handler;
        return this;
    }

    @Override
    public @Nullable PurchaseHandler purchaseHandler() {
        return purchaseHandler;
    }

    @Override
    public @NotNull TradeElement purchaseHandler(@NotNull PurchaseHandler handler) {
        purchaseHandler = handler;
        return this;
    }

    @Override
    public @NotNull MerchantRecipe create() {
        MerchantRecipe merchantRecipe = new MerchantRecipe(result, maxUses);

        for (ItemStack ingredient : ingredients) {
            merchantRecipe.addIngredient(ingredient);
        }

        return merchantRecipe;
    }

    @Override
    public @NotNull TradeElement createCopy() {
        TradeElementImpl copy = new TradeElementImpl(result, ingredients.getIngredient1(), ingredients.getIngredient2());
        copy.maxUses = maxUses;
        copy.selectHandler = selectHandler;
        copy.purchaseHandler = purchaseHandler;
        copy.observers.addAll(observers);
        return copy;
    }

    @ApiStatus.Internal
    public void addObserver(final @NotNull MerchantGuiController observer) {
        observers.add(observer);
    }

    @ApiStatus.Internal
    public void removeObserver(final @NotNull MerchantGuiController observer) {
        observers.remove(observer);
    }

    public boolean equals(final @Nullable MerchantRecipe obj) {
        if (obj == null) {
            return false;
        }

        return obj.getResult().equals(result) && ((obj.getIngredients().stream().allMatch(ingredient -> ingredient.equals(ingredients.getIngredient1()) || ingredient.equals(ingredients.getIngredient2()))));
    }
}
