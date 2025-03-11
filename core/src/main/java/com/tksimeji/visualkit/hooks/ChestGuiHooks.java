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
    default @NotNull ItemSlotPolicy useGetPolicy(final int index) {
        return controller().getPolicy(index);
    }

    @Override
    default void useSetPolicy(final int index, final @NotNull ItemSlotPolicy policy) {
        controller().setPolicy(index, policy);
    }

    @Override
    default @NotNull ItemSlotPolicy useGetDefaultPolicy() {
        return controller().getDefaultPolicy();
    }

    @Override
    default void useSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy) {
        controller().setDefaultPolicy(defaultPolicy);
    }

    @Override
    default @NotNull ItemSlotPolicy useGetPlayerDefaultPolicy() {
        return controller().getPlayerDefaultPolicy();
    }

    @Override
    default void useSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy) {
        controller().setPlayerDefaultPolicy(playerDefaultPolicy);
    }

    @Override
    default @NotNull Locale useLocale() {
        return controller().getLocale();
    }

    @Override
    default boolean useIsEmpty() {
        return useGetElements().isEmpty();
    }

    @Override
    default void useClose() {
        controller().close();
    }

    @Override
    default void useState(final @NotNull String key, final @Nullable Object value) {
        controller().setState(key, value);
    }

    private @NotNull ChestGuiController controller() {
        GuiController controller = Visualkit.getGuiController(this);

        if (!(controller instanceof ChestGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (ChestGuiController) controller;
    }
}
