package com.tksimeji.kunectron.element;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.controller.MerchantGuiController;
import com.tksimeji.kunectron.markupextension.MarkupExtensionSupport;
import com.tksimeji.kunectron.markupextension.context.Context;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.*;

public class TradeElementImpl implements ObservableElement<MerchantRecipe, MerchantGuiController>, TradeElement {
    private @NotNull ItemStack result;

    private @NotNull Ingredients ingredients;

    private int maxUses = Integer.MAX_VALUE;

    private boolean canSelect = true;
    private boolean canPurchase = true;

    private @Nullable SelectHandler selectHandler;

    private @Nullable PurchaseHandler purchaseHandler;

    private final @NotNull Set<MerchantGuiController> observers = new HashSet<>();

    public TradeElementImpl(final @NotNull ItemStack result, final @NotNull ItemStack ingredient1, final @Nullable ItemStack ingredient2) {
        this.result = result.clone();

        if (this.result instanceof MarkupExtensionSupport markupExtensionSupport) {
            markupExtensionSupport.setContext(null);
        }

        ingredients = new IngredientsImpl(ingredient1.clone(), ingredient2 != null ? ingredient2.clone() : null);
    }

    @Override
    public void registerObserver(final @NotNull MerchantGuiController observer) {
        Preconditions.checkArgument(observer != null, "Observer cannot be null.");
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(final @NotNull MerchantGuiController observer) {
        Preconditions.checkArgument(observer != null, "Observer cannot be null.");
        observers.remove(observer);
    }

    private void callObservers() {
        observers.forEach(MerchantGuiController::update);
    }

    @Override
    public @NotNull ItemStack result() {
        return result.clone();
    }

    @Override
    public @NotNull TradeElement result(final @NotNull ItemStack result) {
        Preconditions.checkArgument(result != null, "Result cannot be null.");

        if (result.equals(this.result)) {
            return this;
        }

        this.result = result.clone();
        callObservers();
        return this;
    }

    @Override
    public @NotNull Ingredients ingredients() {
        return ingredients;
    }

    @Override
    public @NotNull TradeElement ingredient(final @NotNull ItemStack ingredient) {
        Preconditions.checkArgument(ingredient != null, "Ingredient cannot be null.");
        return ingredients(ingredient, null);
    }

    @Override
    public @NotNull TradeElement ingredients(final @NotNull ItemStack ingredient1, @Nullable ItemStack ingredient2) {
        Preconditions.checkArgument(ingredient1 != null, "Ingredient1 cannot be null.");

        if (ingredient1.equals(ingredients.getIngredient1()) && Objects.equals(ingredient2, ingredients.getIngredient2())) {
            return this;
        }

        ingredients = new IngredientsImpl(ingredient1, ingredient2);
        callObservers();
        return this;
    }

    @Override
    public int maxUses() {
        return maxUses;
    }

    @Override
    public @NotNull TradeElement maxUses(final @Range(from = 0, to = Integer.MAX_VALUE) int maxUses) {
        Preconditions.checkArgument(0 < maxUses, "Max uses cannot be less than 0.");

        if (maxUses == this.maxUses) {
            return this;
        }

        this.maxUses = maxUses;
        callObservers();
        return this;
    }

    @Override
    public boolean canSelect() {
        return canSelect;
    }

    @Override
    public @NotNull TradeElement canSelect(final boolean canSelect) {
        if (canSelect == this.canSelect) {
            return this;
        }

        this.canSelect = canSelect;
        callObservers();
        return this;
    }

    @Override
    public boolean canPurchase() {
        return canPurchase;
    }

    @Override
    public @NotNull TradeElement canPurchase(final boolean canPurchase) {
        if (canPurchase == this.canPurchase) {
            return this;
        }

        this.canPurchase = canPurchase;
        callObservers();
        return this;
    }

    @Override
    public @Nullable SelectHandler selectHandler() {
        return selectHandler;
    }

    @Override
    public @NotNull TradeElement selectHandler(final @NotNull SelectHandler1 handler) {
        selectHandler = handler;
        return this;
    }

    @Override
    public @NotNull TradeElement selectHandler(final @NotNull SelectHandler2 handler) {
        selectHandler = handler;
        return this;
    }

    @Override
    public @Nullable PurchaseHandler purchaseHandler() {
        return purchaseHandler;
    }

    @Override
    public @NotNull TradeElement purchaseHandler(final @NotNull PurchaseHandler1 handler) {
        purchaseHandler = handler;
        return this;
    }

    @Override
    public @NotNull TradeElement purchaseHandler(final @NotNull PurchaseHandler2 handler) {
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
        return copy;
    }

    @Override
    public boolean equals(final @Nullable MerchantRecipe object) {
        if (object == null) {
            return false;
        }
        return object.getResult().equals(result) && (object.getIngredients().stream().allMatch(ingredient -> ingredient.equals(ingredients.getIngredient1()) || ingredient.equals(ingredients.getIngredient2())));
    }

    private static final class IngredientsImpl implements Ingredients {
        private final @NotNull ItemStack ingredient1;
        private final @Nullable ItemStack ingredient2;

        private @Nullable Context<?> markupExtensionContext;

        public IngredientsImpl(final @NotNull ItemStack ingredient) {
            this(ingredient, null);
        }

        public IngredientsImpl(final @NotNull ItemStack ingredient1, final @Nullable ItemStack ingredient2) {
            this.ingredient1 = ingredient1.clone();
            this.ingredient2 = ingredient2 != null ? ingredient2.clone() : null;
        }

        @Override
        public @NotNull ItemStack getIngredient1() {
            return ingredient1.clone();
        }

        @Override
        public @Nullable ItemStack getIngredient2() {
            return ingredient2 != null ? ingredient2.clone() : null;
        }

        @Override
        public boolean hasIngredient2() {
            return ingredient2 != null;
        }

        @Override
        public @NotNull Iterator<ItemStack> iterator() {
            List<ItemStack> collection = new ArrayList<>();
            collection.add(ingredient1);

            if (hasIngredient2()) {
                collection.add(ingredient2);
            }

            return collection.iterator();
        }
    }
}
