package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface ItemContainerGuiHooks extends ContainerGuiHooks {
    @NotNull ItemSlotPolicy usePolicy(final int index);

    @NotNull ItemSlotPolicy usePolicy(final int index, final boolean player);

    void usePolicy(final int index, final @NotNull ItemSlotPolicy policy);

    void usePolicy(final int index, final @NotNull ItemSlotPolicy policy, final boolean player);

    @NotNull ItemSlotPolicy useDefaultPolicy();

    void useDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy);

    @NotNull ItemSlotPolicy usePlayerDefaultPolicy();

    void usePlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy);

    @NotNull Locale useLocale();

    boolean useIsEmpty();
}
