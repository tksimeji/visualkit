package com.tksimeji.kunectron.policy;

import org.jetbrains.annotations.NotNull;

public interface Policy {
    static @NotNull ItemSlotPolicy itemSlot(final boolean variation) {
        return new ItemSlotPolicyImpl(variation);
    }
}
