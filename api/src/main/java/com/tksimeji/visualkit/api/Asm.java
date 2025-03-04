package com.tksimeji.visualkit.api;

import org.jetbrains.annotations.ApiStatus;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
public @interface Asm {
    int[] value() default {};

    int from() default -1;

    int to() default -1;
}
