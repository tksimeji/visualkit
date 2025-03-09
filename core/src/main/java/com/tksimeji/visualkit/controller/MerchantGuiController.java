package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.element.TradeElement;
import com.tksimeji.visualkit.type.MerchantGuiType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantInventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public sealed interface MerchantGuiController extends ContainerGuiController<MerchantInventory> permits MerchantGuiControllerImpl {
    @ApiStatus.Internal
    static @Nullable MerchantGuiController get(final @NotNull Player player) {
        return Visualkit.getGuiControllers(MerchantGuiType.instance())
                .stream()
                .filter(controller -> controller.getPlayer() == player)
                .findFirst()
                .orElse(null);
    }

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
