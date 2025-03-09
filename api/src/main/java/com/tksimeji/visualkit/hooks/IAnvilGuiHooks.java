package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface IAnvilGuiHooks extends Hooks {
    @NotNull Player usePlayer();

    @Nullable ItemElement useGetFirstElement();

    void useSetFirstElement(final @Nullable ItemElement element);

    @Nullable ItemElement useGetSecondElement();

    void useSetSecondElement(final @Nullable ItemElement element);

    @Nullable ItemElement useGetResultElement();

    void useSetResultElement(final @Nullable ItemElement element);

    @NotNull ItemSlotPolicy useGetPolicy(final int index);

    void useSetPolicy(final int index, final @NotNull ItemSlotPolicy policy);

    @NotNull ItemSlotPolicy useGetDefaultPolicy();

    void useSetDefaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy);

    @NotNull ItemSlotPolicy useGetPlayerDefaultPolicy();

    void useSetPlayerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy);

    void useClose();
}
