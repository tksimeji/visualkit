package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Product;
import com.tksimeji.visualkit.product.VisualkitProduct;
import com.tksimeji.visualkit.util.ReflectionUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

@ApiStatus.Experimental
public abstract class VillagerUI extends ContainerUI<MerchantInventory> implements IVillagerUI {
    private final @NotNull Merchant merchant = Bukkit.createMerchant(title());

    private final @NotNull List<VisualkitProduct> products = new ArrayList<>();
    private final @NotNull Set<Field> crawledFields = new HashSet<>();

    public VillagerUI(@NotNull Player player) {
        super(player);
        push();
    }

    @Override
    public final @NotNull VisualkitProduct getProduct(int index) {
        return products.get(index);
    }

    @Override
    public final void setProduct(int index, @NotNull VisualkitProduct product) {
        products.set(index, product);
        push();
    }

    @Override
    public final void addProduct(@NotNull VisualkitProduct product) {
        setProduct(size(), product);
    }

    @Override
    public final void removeProduct(int index) {
        products.remove(index);
        push();
    }

    @Override
    public final void removeProduct(@NotNull VisualkitProduct product) {
        removeProduct(products.indexOf(product));
    }

    @Override
    public final @NotNull MerchantInventory asInventory() {
        return (MerchantInventory) player.getOpenInventory().getTopInventory();
    }

    @Override
    public final boolean isEmpty() {
        return products.isEmpty();
    }

    @Override
    public final void clear() {
        products.clear();
        push();
    }

    @Override
    public final int size() {
        return products.size();
    }

    @Override
    public final void tick() {
        ReflectionUtility.getFields(getClass()).stream()
                .filter(field -> ! crawledFields.contains(field) &&
                        field.isAnnotationPresent(Product.class) &&
                        VisualkitProduct.class.isAssignableFrom(field.getType()))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        Product annotation = field.getAnnotation(Product.class);
                        int index = annotation.index();

                        if (field.get(VillagerUI.this) instanceof VisualkitProduct product) {
                            setProduct(Math.max(index, size()), product);
                            crawledFields.add(field);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void push() {
        merchant.setRecipes(products.stream().map(VisualkitProduct::asMerchantRecipe).toList());
        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> player.openMerchant(merchant, true));
    }
}
