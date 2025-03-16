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
    default @NotNull Player usePlayer() {
        return controller().getPlayer();
    }

    default @Nullable ItemElement useFirstElement() {
        return controller().getFirstElement();
    }

    default void useFirstElement(final @Nullable ItemElement element) {
        controller().setFirstElement(element);
    }

    default @Nullable ItemElement useSecondElement() {
        return controller().getSecondElement();
    }

    default void useSecondElement(final @Nullable ItemElement element) {
        controller().setSecondElement(element);
    }

    default @Nullable ItemElement useResultElement() {
        return controller().getResultElement();
    }

    default void useResultElement(final @Nullable ItemElement element) {
        controller().setResultElement(element);
    }

    @Override
    default @NotNull ItemSlotPolicy usePolicy(final int index) {
        return usePolicy(index, false);
    }

    @Override
    default @NotNull ItemSlotPolicy usePolicy(final int index, final boolean player) {
        return controller().getPolicy(player ? index + controller().getSize() : index);
    }

    @Override
    default void usePolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        usePolicy(index, policy, false);
    }

    @Override
    default void usePolicy(final int index, final @NotNull ItemSlotPolicy policy, final boolean player) {
        Preconditions.checkArgument(policy != null, "Policy cannot be null.");
        controller().setPolicy(player ? controller().getSize() + index : index, policy);
    }

    @Override
    default @NotNull ItemSlotPolicy useDefaultPolicy() {
        return controller().getDefaultPolicy();
    }

    @Override
    default void useDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        Preconditions.checkArgument(defaultPolicy != null, "Default policy cannot be null.");
        controller().setDefaultPolicy(defaultPolicy);
    }

    @Override
    default @NotNull ItemSlotPolicy usePlayerDefaultPolicy() {
        return controller().getPlayerDefaultPolicy();
    }

    @Override
    default void usePlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        Preconditions.checkArgument(playerDefaultPolicy != null, "Player default policy cannot be null.");
        controller().setPlayerDefaultPolicy(playerDefaultPolicy);
    }

    @Override
    default boolean useIsEmpty() {
        return this.useFirstElement() == null && this.useSecondElement() == null && this.useResultElement() == null;
    }

    @Override
    default @NotNull Locale useLocale() {
        return controller().getLocale();
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

    private @NotNull AnvilGuiController controller() {
        GuiController controller = Kunectron.getGuiController(this);

        if (!(controller instanceof AnvilGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (AnvilGuiController) controller;
    }
}
