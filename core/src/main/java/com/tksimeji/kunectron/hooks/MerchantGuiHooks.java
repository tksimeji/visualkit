package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.GuiController;
import com.tksimeji.kunectron.controller.MerchantGuiController;
import com.tksimeji.kunectron.element.TradeElement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MerchantGuiHooks extends IMerchantGuiHooks {
    @Override
    default @NotNull Player hookPlayer() {
        return controller().getPlayer();
    }

    @Override
    default @Nullable TradeElement hookGetElement(final int index) {
        return controller().getElement(index);
    }

    @Override
    default @NotNull List<TradeElement> hookGetElements() {
        return controller().getElements();
    }

    @Override
    default void hookSetElement(final int index, final @NotNull TradeElement element) {
        controller().setElement(index, element);
    }

    @Override
    default void hookAddElement(final @NotNull TradeElement element) {
        controller().addElement(element);
    }

    @Override
    default void hookRemoveElement(final int index) {
        controller().removeElement(index);
    }

    @Override
    default void hookInsertElement(final int index, final @NotNull TradeElement element) {
        controller().insertElement(index, element);
    }

    @Override
    default void hookClose() {
        controller().close();
    }

    @Override
    default void hookState(final @NotNull String key, final @Nullable Object value) {
        controller().setState(key, value);
    }

    private @NotNull MerchantGuiController controller() {
        GuiController controller = Kunectron.getGuiController(this);

        if (!(controller instanceof MerchantGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (MerchantGuiController) controller;
    }
}
