package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.ChestGuiController;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface ChestGuiHooks extends IChestGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        ChestGuiController controller = (ChestGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        return controller.getPlayer();
    }

    @Override
    default @Nullable ItemElement useGetElement(final int index) {
        ChestGuiController controller = (ChestGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        return controller.getElement(index);
    }

    @Override
    default @NotNull Map<Integer, ItemElement> useGetElements() {
        ChestGuiController controller = (ChestGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        return controller.getElements();
    }

    @Override
    default void useSetElement(final int index, final @NotNull ItemElement element) {
        ChestGuiController controller = (ChestGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        controller.setElement(index, element);
    }

    @Override
    default void useClearElement(final int index) {
        ChestGuiController controller = (ChestGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        controller.setElement(index, null);
    }

    @Override
    default void useClose() {
        ChestGuiController controller = (ChestGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        controller.close();
    }
}
