package com.tksimeji.kunectron.hooks;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.AnvilGuiController;
import com.tksimeji.kunectron.controller.GuiController;
import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public interface AnvilGuiHooks extends IAnvilGuiHooks {
    @Override
    default @NotNull Player hookPlayer() {
        return controller().getPlayer();
    }

    default @Nullable ItemElement hookGetFirstElement() {
        return controller().getFirstElement();
    }

    default void hookSetFirstElement(final @Nullable ItemElement element) {
        controller().setFirstElement(element);
    }

    default @Nullable ItemElement hookGetSecondElement() {
        return controller().getSecondElement();
    }

    default void hookSetSecondElement(final @Nullable ItemElement element) {
        controller().setSecondElement(element);
    }

    default @Nullable ItemElement hookGetResultElement() {
        return controller().getResultElement();
    }

    default void hookSetResultElement(final @Nullable ItemElement element) {
        controller().setResultElement(element);
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetPolicy(final int index) {
        return controller().getPolicy(index);
    }

    @Override
    default void hookSetPolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        Preconditions.checkArgument(policy != null, "Policy cannot be null.");
        controller().setPolicy(index, policy);
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetDefaultPolicy() {
        return controller().getDefaultPolicy();
    }

    @Override
    default void hookSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        Preconditions.checkArgument(defaultPolicy != null, "Default policy cannot be null.");
        controller().setDefaultPolicy(defaultPolicy);
    }

    @Override
    default @NotNull ItemSlotPolicy hookGetPlayerDefaultPolicy() {
        return controller().getPlayerDefaultPolicy();
    }

    @Override
    default void hookSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        Preconditions.checkArgument(playerDefaultPolicy != null, "Player default policy cannot be null.");
        controller().setPlayerDefaultPolicy(playerDefaultPolicy);
    }

    @Override
    default boolean hookIsEmpty() {
        return this.hookGetFirstElement() == null && this.hookGetSecondElement() == null && this.hookGetResultElement() == null;
    }

    @Override
    default @NotNull Locale hookLocale() {
        return controller().getLocale();
    }

    @Override
    default void hookClose() {
        controller().close();
    }

    @Override
    default void hookState(final @NotNull String key, final @Nullable Object value) {
        Preconditions.checkArgument(key != null, "Key cannot be null.");
        controller().setState(key, value);
    }

    private @NotNull AnvilGuiController controller() {
        GuiController controller = Kunectron.getGuiController(this);

        if (!(controller instanceof AnvilGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (AnvilGuiController) controller;
    }
}
