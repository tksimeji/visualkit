package com.tksimeji.visualkit;

import com.tksimeji.visualkit.product.VisualkitProduct;
import org.bukkit.inventory.MerchantInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IVillagerUI extends IContainerUI<MerchantInventory> {
    /**
     * Gets any product.
     *
     * @param index Product list position
     * @return Product
     */
    @Nullable VisualkitProduct getProduct(int index);

    /**
     * Sets any product.
     *
     * @param index Product list position
     * @param product Product
     */
    void setProduct(int index, @NotNull VisualkitProduct product);

    /**
     * Add any product.
     *
     * @param product Product
     */
    void addProduct(@NotNull VisualkitProduct product);

    /**
     * Remove any product.
     *
     * @param index Product list position
     */
    void removeProduct(int index);

    /**
     * Remove any product.
     *
     * @param product Product
     */
    void removeProduct(@NotNull VisualkitProduct product);

    /**
     * Determine if the product list is empty.
     *
     * @return True if there are no products
     */
    boolean isEmpty();

    /**
     * Delete all products
     */
    void clear();

    /**
     * Gets the size of the product list.
     *
     * @return Product list size
     */
    int size();
}
