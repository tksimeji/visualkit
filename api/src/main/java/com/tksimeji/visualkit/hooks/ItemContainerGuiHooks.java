package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface ItemContainerGuiHooks extends ContainerGuiHooks {
    @NotNull ItemSlotPolicy useGetPolicy(final int index);

    void useSetPolicy(final int index, final @NotNull ItemSlotPolicy policy);

    @NotNull ItemSlotPolicy useGetDefaultPolicy();

    void useSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy);

    @NotNull ItemSlotPolicy useGetPlayerDefaultPolicy();

    void useSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy);

    @NotNull Locale useLocale();

    boolean useIsEmpty();
}
