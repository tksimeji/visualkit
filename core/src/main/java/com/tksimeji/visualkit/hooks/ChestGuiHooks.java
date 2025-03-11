package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.ChestGuiController;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

public interface ChestGuiHooks extends IChestGuiHooks {
    @Override
    default @NotNull Player hookPlayer() {
        return controller().getPlayer();
    }

    @Override
    default @Nullable ItemElement hookGetElement(final int index) {
        return controller().getElement(index);
    }

    @Override
    default @NotNull Map<Integer, ItemElement> hookGetElements() {
        return controller().getElements();
    }

    @Override
    default void hookSetElement(final int index, final @NotNull ItemElement element) {
        controller().setElement(index, element);
    }

    @Override
    default void hookClearElement(final int index) {
        controller().setElement(index, null);
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetPolicy(final int index) {
        return controller().getPolicy(index);
    }

    @Override
    default void hookSetPolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        controller().setPolicy(index, policy);
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetDefaultPolicy() {
        return controller().getDefaultPolicy();
    }

    @Override
    default void hookSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        controller().setDefaultPolicy(defaultPolicy);
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetPlayerDefaultPolicy() {
        return controller().getPlayerDefaultPolicy();
    }

    @Override
    default void hookSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        controller().setPlayerDefaultPolicy(playerDefaultPolicy);
    }

    @Override
    default @NotNull Locale hookLocale() {
        return controller().getLocale();
    }

    @Override
    default boolean hookIsEmpty() {
        return this.hookGetElements().isEmpty();
    }

    @Override
    default void hookClose() {
        controller().close();
    }

    @Override
    default void hookState(final @NotNull String name, final @Nullable Object value) {
        controller().setState(name, value);
    }

    private @NotNull ChestGuiController controller() {
        GuiController controller = Visualkit.getGuiController(this);

        if (!(controller instanceof ChestGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (ChestGuiController) controller;
    }
}
