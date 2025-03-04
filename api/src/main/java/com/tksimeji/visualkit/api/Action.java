package com.tksimeji.visualkit.api;

import org.jetbrains.annotations.ApiStatus;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
public enum Action {
    SINGLE_CLICK,
    DOUBLE_CLICK,
    SHIFT_CLICK,
    QUICK_MOVE,
    DRAG,
    SELECT,
    PURCHASE
}
