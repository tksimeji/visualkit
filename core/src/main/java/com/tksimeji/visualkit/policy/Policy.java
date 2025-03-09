package com.tksimeji.visualkit.policy;

import org.jetbrains.annotations.NotNull;

public interface Policy {
    static @NotNull ItemSlotPolicy itemSlot() {
        return itemSlot(false);
    }

    static @NotNull ItemSlotPolicy itemSlot(final boolean variation) {
        return new ItemSlotPolicyImpl(variation);
    }
}
