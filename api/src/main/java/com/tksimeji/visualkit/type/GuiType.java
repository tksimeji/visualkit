package com.tksimeji.visualkit.type;

import com.tksimeji.visualkit.controller.GuiController;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public interface GuiType<A extends Annotation, C extends GuiController> {
    @NotNull Class<A> getAnnotationClass();

    @NotNull Class<C> getControllerClass();

    @NotNull C createController(final @NotNull Object object, final @NotNull A annotation);
}
