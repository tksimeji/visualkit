package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.controller.MerchantGuiController;
import com.tksimeji.visualkit.element.TradeElement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MerchantGuiHooks extends IMerchantGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        return controller().getPlayer();
    }

    @Override
    default @Nullable TradeElement useGetElement(final int index) {
        return controller().getElement(index);
    }

    @Override
    default @NotNull List<TradeElement> useGetElements() {
        return controller().getElements();
    }

    @Override
    default void useSetElement(final int index, final @NotNull TradeElement element) {
        controller().setElement(index, element);
    }

    @Override
    default void useAddElement(final @NotNull TradeElement element) {
        controller().addElement(element);
    }

    @Override
    default void useRemoveElement(final int index) {
        controller().removeElement(index);
    }

    @Override
    default void useInsertElement(final int index, final @NotNull TradeElement element) {
        controller().insertElement(index, element);
    }

    @Override
    default void useClose() {
        controller().close();
    }

    private @NotNull MerchantGuiController controller() {
        GuiController controller = Visualkit.getGuiController(this);

        if (!(controller instanceof MerchantGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (MerchantGuiController) controller;
    }
}
