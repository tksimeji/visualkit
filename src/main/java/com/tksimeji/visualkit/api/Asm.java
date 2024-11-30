package com.tksimeji.visualkit.api;

public @interface Asm {
    int[] value() default {};
    int from() default -1;
    int to() default  -1;
}
