package com.tksimeji.kunectron.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GuiHandler {
    int priority() default -1;
    boolean async() default false;
    boolean ignoreCancelled() default false;
}
