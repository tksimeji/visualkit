package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface ItemContainerGuiHooks extends ContainerGuiHooks {
    @NotNull ItemSlotPolicy hookGetPolicy(final int index);

    void hookSetPolicy(final int index, final @NotNull ItemSlotPolicy policy);

    @NotNull ItemSlotPolicy hookGetDefaultPolicy();

    void hookSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy);

    @NotNull ItemSlotPolicy hookGetPlayerDefaultPolicy();

    void hookSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy);

    @NotNull Locale hookLocale();

    boolean hookIsEmpty();
}
