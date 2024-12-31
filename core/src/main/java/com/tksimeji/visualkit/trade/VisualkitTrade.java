package com.tksimeji.visualkit.trade;

import com.tksimeji.visualkit.MerchantUI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class VisualkitTrade {
    /**
     * Create a new trade.
     *
     * @param item Merchandise
     * @return New trade
     */
    public static @NotNull VisualkitTrade create(@NotNull ItemStack item) {
        return new VisualkitTrade(item);
    }

    /**
     * Create a new trade.
     *
     * @param item Merchandise
     * @param requirement Items required for purchase
     * @return New trade
     */
    public static @NotNull VisualkitTrade create(@NotNull ItemStack item, @Nullable ItemStack requirement) {
        return create(item, requirement, null);
    }

    /**
     * Create a new trade.
     *
     * @param item Merchandise
     * @param requirement1 Items required for purchase
     * @param requirement2 Items required for purchase
     * @return New trade
     */
    public static @NotNull VisualkitTrade create(@NotNull ItemStack item, @Nullable ItemStack requirement1, @Nullable ItemStack requirement2) {
        return new VisualkitTrade(item).price(requirement1, requirement2);
    }

    private @NotNull ItemStack item;

    private @Nullable Requirement requirement;

    private @Nullable SelectHandler handler1;
    private @Nullable PurchaseHandler handler2;

    private boolean purchasable = true;

    private final @NotNull Set<MerchantUI> observers = new HashSet<>();

    private VisualkitTrade(@NotNull ItemStack item) {
        item(item);
    }

    /**
     * Gets the merchandise
     *
     * @return Updated trade
     */
    public @NotNull ItemStack item() {
        return item;
    }

    /**
     * Sets the merchandise
     *
     * @param item Merchandise
     * @return Updated trade
     */
    public @NotNull VisualkitTrade item(@NotNull ItemStack item) {
        if (Objects.equals(this.item, item)) {
            return this;
        }

        this.item = item;
        observers.forEach(MerchantUI::push);
        return this;
    }

    /**
     * Gets purchase requirement.
     *
     * @return Requirement
     */
    public @Nullable Requirement price() {
        return requirement;
    }

    /**
     * Sets purchase requirement.
     *
     * @param item Requirement
     * @return Updated trade
     */
    public @NotNull VisualkitTrade price(@Nullable ItemStack item) {
        return price(item, null);
    }

    /**
     * Sets purchase requirement.
     *
     * @param item1 Requirement
     * @param item2 Requirement
     * @return Updated trade
     */
    public @NotNull VisualkitTrade price(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        if ((requirement != null && Objects.equals(requirement.item1(), item1) && Objects.equals(requirement.item2(), item2)) ||
                (requirement == null && item1 == null && item2 == null)) {
            return this;
        }

        requirement = new Requirement(item1, item2);
        observers.forEach(MerchantUI::push);
        return this;
    }

    /**
     * Gets the handler to be called on select.
     *
     * @return Handler
     */
    public @Nullable SelectHandler onSelect() {
        return handler1;
    }

    /**
     * Sets the handler to be called on select.
     *
     * @param handler Handler
     * @return Updated trade
     */
    public @NotNull VisualkitTrade onSelect(@Nullable SelectHandler handler) {
        handler1 = handler;
        return this;
    }

    /**
     * Gets the handler to be called on purchase.
     *
     * @return Handler
     */
    public @Nullable PurchaseHandler onPurchase() {
        return handler2;
    }

    /**
     * Sets the handler to be called on purchase.
     *
     * @param handler Handler
     * @return Updated trade
     */
    public @NotNull VisualkitTrade onPurchase(@Nullable PurchaseHandler handler) {
        handler2 = handler;
        return this;
    }

    /**
     * Gets whether the player can purchase.
     *
     * @return True if available
     */
    public boolean purchasable() {
        return purchasable;
    }

    /**
     * Sets whether the player can purchase.
     *
     * @param purchasable Availability for purchase
     * @return Updated trade
     */
    public @NotNull VisualkitTrade purchasable(boolean purchasable) {
        this.purchasable = purchasable;
        return this;
    }

    /**
     * Add an object to track updates.
     * Note: This is for internal use only. If you do not understand what this is, do not use it.
     *
     * @param observer Observer
     */
    public void addObserver(@NotNull MerchantUI observer) {
        observers.add(observer);
    }

    /**
     * Remove an object to track update.
     * Note: This is for internal use only. If you do not understand what this is, do not use it.
     *
     * @param observer Observer
     */
    public void removeObserver(@NotNull MerchantUI observer) {
        observers.remove(observer);
    }

    /**
     * Build an {@link MerchantRecipe}
     *
     * @return MerchantRecipe
     */
    public @NotNull MerchantRecipe asRecipe() {
        MerchantRecipe merchantRecipe = new MerchantRecipe(item, Integer.MAX_VALUE);

        if (requirement != null) {
            Optional.ofNullable(requirement.item1()).ifPresent(merchantRecipe::addIngredient);
            Optional.ofNullable(requirement.item2()).ifPresent(merchantRecipe::addIngredient);
        }

        return merchantRecipe;
    }

    /**
     * Determines if {@link MerchantRecipe} represent the same content.
     *
     * @param recipe Target
     * @return Recipe
     */
    public boolean equals(@Nullable MerchantRecipe recipe) {
        if (recipe == null) {
            return false;
        }

        return recipe.getResult().equals(item) &&
                ((requirement != null && recipe.getIngredients().stream().allMatch(ingredient -> ingredient.equals(requirement.item1()) || ingredient.equals(requirement.item2()))) ||
                        recipe.getIngredients().isEmpty());
    }

    public interface SelectHandler {
        void onSelect();
    }

    public interface PurchaseHandler {
        void onPurchase();
    }
}
