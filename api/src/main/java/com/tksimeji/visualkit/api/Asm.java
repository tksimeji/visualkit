package com.tksimeji.visualkit.api;

@Deprecated(since = "1.0.0", forRemoval = true)
public @interface Asm {
    int[] value() default {};

    int from() default -1;

    int to() default -1;
}
