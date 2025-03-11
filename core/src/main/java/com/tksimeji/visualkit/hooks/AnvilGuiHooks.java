package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.AnvilGuiController;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

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
    default boolean useIsEmpty() {
        return useGetFirstElement() == null && useGetSecondElement() == null && useGetResultElement() == null;
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
        controller().setState(key, value);
    }

    private @NotNull AnvilGuiController controller() {
        GuiController controller = Visualkit.getGuiController(this);

        if (!(controller instanceof AnvilGuiController)) {
            throw new IllegalStateException("No gui controller found.");
        }

        return (AnvilGuiController) controller;
    }
}
