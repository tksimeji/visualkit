package com.tksimeji.visualkit.api;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
    int[] index() default {};

    Asm[] asm() default {};

    @NotNull Action[] action() default {Action.SINGLE_CLICK, Action.DOUBLE_CLICK, Action.SHIFT_CLICK, Action.QUICK_MOVE, Action.DRAG, Action.SELECT, Action.PURCHASE};

    @NotNull Mouse[] mouse() default {Mouse.LEFT, Mouse.RIGHT};
}
