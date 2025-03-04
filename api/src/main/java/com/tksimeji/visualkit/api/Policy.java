package com.tksimeji.visualkit.api;

import com.tksimeji.visualkit.policy.PolicyTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated(since = "1.0.0", forRemoval = true)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Policy {
    int[] value() default {};

    Asm[] asm() default {};

    PolicyTarget target() default PolicyTarget.UI;
}
