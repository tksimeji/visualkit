package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.AnvilGuiController;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnvilGuiHooks extends IAnvilGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        return controller.getPlayer();
    }

    default @Nullable ItemElement useGetFirstElement() {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        return controller.getFirstElement();
    }

    default void useSetFirstElement(final @Nullable ItemElement element) {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        controller.setFirstElement(element);
    }

    default @Nullable ItemElement useGetSecondElement() {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        return controller.getSecondElement();
    }

    default void useSetSecondElement(final @Nullable ItemElement element) {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        controller.setSecondElement(element);
    }

    default @Nullable ItemElement useGetResultElement() {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        return controller.getResultElement();
    }

    default void useSetResultElement(final @Nullable ItemElement element) {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        controller.setResultElement(element);
    }

    default void useClose() {
        AnvilGuiController controller = (AnvilGuiController) Visualkit.getGuiController(this);
        assert controller != null;
        controller.close();
    }
}
