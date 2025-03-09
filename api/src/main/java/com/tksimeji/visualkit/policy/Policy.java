package com.tksimeji.visualkit.policy;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public interface Policy {
    static @NotNull ItemSlotPolicy itemSlot() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull ItemSlotPolicy itemSlot(final boolean fixation) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
