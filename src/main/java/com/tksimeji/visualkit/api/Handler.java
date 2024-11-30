package com.tksimeji.visualkit.api;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
    int[] slot() default {};
    Asm[] asm() default {};
    @NotNull Click[] click() default {Click.SINGLE, Click.DOUBLE, Click.SHIFT};
    @NotNull Mouse[] mouse() default {Mouse.LEFT, Mouse.RIGHT};
}
