package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.AnvilGuiController;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnvilGuiHooks extends IAnvilGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        return controller().getPlayer();
    }

    default @Nullable ItemElement useGetFirstElement() {
        return controller().getFirstElement();
    }

    default void useSetFirstElement(final @Nullable ItemElement element) {
        controller().setFirstElement(element);
    }

    default @Nullable ItemElement useGetSecondElement() {
        return controller().getSecondElement();
    }

    default void useSetSecondElement(final @Nullable ItemElement element) {
        controller().setSecondElement(element);
    }

    default @Nullable ItemElement useGetResultElement() {
        return controller().getResultElement();
    }

    default void useSetResultElement(final @Nullable ItemElement element) {
        controller().setResultElement(element);
    }

    default void useClose() {
        controller().close();
    }

    private @NotNull AnvilGuiController controller() {
        GuiController controller = Visualkit.getGuiController(this);

        if (!(controller instanceof AnvilGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (AnvilGuiController) controller;
    }
}
