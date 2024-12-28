package com.tksimeji.visualkit.product;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class VisualkitProduct {
    public static @NotNull VisualkitProduct create(@NotNull ItemStack result) {
        return new VisualkitProduct(result);
    }

    private @NotNull ItemStack item;
    private final @NotNull Price price = new Price(null, null);

    private VisualkitProduct(@NotNull ItemStack item) {
        item(item);
    }

    public @NotNull ItemStack item() {
        return item;
    }

    public @NotNull VisualkitProduct item(@NotNull ItemStack result) {
        this.item = result;
        return this;
    }

    public @NotNull Price price() {
        return price;
    }

    public @NotNull VisualkitProduct price(@Nullable ItemStack item) {
        return price(item, null);
    }

    public @NotNull VisualkitProduct price(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        price.setItem1(item1);
        price.setItem2(item2);
        return this;
    }

    public @NotNull MerchantRecipe asMerchantRecipe() {
        MerchantRecipe merchantRecipe = new MerchantRecipe(item, Integer.MAX_VALUE);
        Optional.ofNullable(price.getItem1()).ifPresent(merchantRecipe::addIngredient);
        Optional.ofNullable(price.getItem2()).ifPresent(merchantRecipe::addIngredient);
        return merchantRecipe;
    }
}
