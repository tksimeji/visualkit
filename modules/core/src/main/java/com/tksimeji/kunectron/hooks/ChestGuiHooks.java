package com.tksimeji.kunectron.hooks;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.ChestGuiController;
import com.tksimeji.kunectron.controller.GuiController;
import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

public interface ChestGuiHooks extends IChestGuiHooks {
    @Override
    default @NotNull Player usePlayer() {
        return controller().getPlayer();
    }

    @Override
    default @Nullable ItemElement useElement(final int index) {
        return controller().getElement(index);
    }

    @Override
    default void useElement(final int index, final @NotNull ItemElement element) {
        controller().setElement(index, element);
    }

    @Override
    default void useClearElement(final int index) {
        controller().setElement(index, null);
    }

    @Override
    default @NotNull Map<Integer, ItemElement> useElements() {
        return controller().getElements();
    }

    @Override
    default @NotNull ItemSlotPolicy usePolicy(final int index) {
        return usePolicy(index, false);
    }

    @Override
    default @NotNull ItemSlotPolicy usePolicy(final int index, final boolean player) {
        return controller().getPolicy(player ? controller().getSize() + index : index);
    }

    @Override
    default void usePolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        usePolicy(index, policy);
    }

    @Override
    default void usePolicy(final int index, final @NotNull ItemSlotPolicy policy, final boolean player) {
        Preconditions.checkArgument(policy != null, "Policy cannot be null.");
        controller().setPolicy(player ? index + controller().getSize() : index, policy);
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
    default @NotNull Locale useLocale() {
        return controller().getLocale();
    }

    @Override
    default boolean useIsEmpty() {
        return this.useElements().isEmpty();
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

    private @NotNull ChestGuiController controller() {
        GuiController controller = Kunectron.getGuiController(this);

        if (!(controller instanceof ChestGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (ChestGuiController) controller;
    }
}
