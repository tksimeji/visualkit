package com.tksimeji.kunectron.hooks;

import com.google.common.base.Preconditions;
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
    default @NotNull Player usePlayer() {
        return controller().getPlayer();
    }

    @Override
    default @Nullable TradeElement useElement(final int index) {
        return controller().getElement(index);
    }

    @Override
    default void useElement(final int index, final @NotNull TradeElement element) {
        Preconditions.checkArgument(element != null, "Element cannot be null.");
        controller().setElement(index, element);
    }

    @Override
    default void useAddElement(final @NotNull TradeElement element) {
        Preconditions.checkArgument(element != null, "Element cannot be null.");
        controller().addElement(element);
    }

    @Override
    default void useRemoveElement(final int index) {
        controller().removeElement(index);
    }

    @Override
    default void useInsertElement(final int index, final @NotNull TradeElement element) {
        Preconditions.checkArgument(element != null, "Element cannot be null.");
        controller().insertElement(index, element);
    }

    @Override
    default @NotNull List<TradeElement> useElements() {
        return controller().getElements();
    }

    @Override
    default void useClose() {
        controller().close();
    }

    @Override
    default void useState(final @NotNull String key, final @Nullable Object value) {
        Preconditions.checkArgument(key != null, "Key cannot be null.");
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
