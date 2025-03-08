package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.ChestGuiController;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface ChestGuiHooks extends IChestGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        return controller().getPlayer();
    }

    @Override
    default @Nullable ItemElement useGetElement(final int index) {
        return controller().getElement(index);
    }

    @Override
    default @NotNull Map<Integer, ItemElement> useGetElements() {
        return controller().getElements();
    }

    @Override
    default void useSetElement(final int index, final @NotNull ItemElement element) {
        controller().setElement(index, element);
    }

    @Override
    default void useClearElement(final int index) {
        controller().setElement(index, null);
    }

    @Override
    default void useClose() {
        controller().close();
    }

    private @NotNull ChestGuiController controller() {
        GuiController controller = Visualkit.getGuiController(this);

        if (!(controller instanceof ChestGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (ChestGuiController) controller;
    }
}
