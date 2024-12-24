package com.tksimeji.visualkit.api;

import com.tksimeji.visualkit.policy.PolicyTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Policy {
    int[] value() default {};

    Asm[] asm() default {};

    PolicyTarget target() default PolicyTarget.UI;
}
