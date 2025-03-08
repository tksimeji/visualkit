package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.element.TradeElement;
import org.bukkit.inventory.MerchantInventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MerchantGuiController extends ContainerGuiController<MerchantInventory> {
    @Nullable TradeElement getElement(final int index);

    @NotNull List<TradeElement> getElements();

    boolean setElement(final int index, final @NotNull TradeElement element);

    void addElement(final @NotNull TradeElement element);

    void removeElement(final int index);

    void insertElement(final int index, final @NotNull TradeElement element);

    boolean select(final int index, final @NotNull TradeElement element);

    boolean purchase(final int index, final @NotNull TradeElement element);

    @ApiStatus.Internal
    void update();
}
