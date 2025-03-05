package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.event.Handler;
import com.tksimeji.visualkit.event.VisualkitEvent;
import com.tksimeji.visualkit.util.ReflectionUtility;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class GuiControllerImpl implements GuiController {
    protected final @NotNull Object gui;

    protected final @NotNull Set<Method> handlers = new LinkedHashSet<>();

    public GuiControllerImpl(final @NotNull Object gui) {
        this.gui = gui;

        ReflectionUtility.getMethods(gui.getClass()).stream()
                .filter(method -> method.isAnnotationPresent(Handler.class) &&
                        method.getParameters().length == 1 &&
                        VisualkitEvent.class.isAssignableFrom(method.getParameterTypes()[0]))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(Handler.class).priority()))
                .forEach(handlers::add);
    }

    @Override
    public @NotNull Object getGui() {
        return gui;
    }

    @Override
    public boolean callEvent(final @NotNull VisualkitEvent event) {
        List<Method> handlers = this.handlers.stream().filter(handler -> event.getClass().isAssignableFrom(handler.getParameterTypes()[0])).toList();

        for (Method handler : handlers) {
            if (event instanceof Cancellable && ((Cancellable) event).isCancelled() && handler.getAnnotation(Handler.class).ignoreCancelled()) {
                continue;
            }

            handler.setAccessible(true);

            try {
                handler.invoke(gui, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return event instanceof Cancellable && ((Cancellable) event).isCancelled();
    }

    @ApiStatus.Internal
    protected final <T> @Nullable T getDeclaration(final @NotNull Object gui, final @NotNull Class<? extends Annotation> annotation, final @NotNull Class<T> aClass) {
        return ReflectionUtility.getFields(gui.getClass()).stream()
                .filter(field -> field.isAnnotationPresent(annotation) && aClass.isAssignableFrom(field.getType()))
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return (T) field.get(gui);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst()
                .orElse(null);
    }

    @ApiStatus.Internal
    protected final  <T> @NotNull T getDeclarationOrDefault(final @NotNull Object gui, final @NotNull Class<? extends Annotation> annotation, final @NotNull Class<T> aClass, final @NotNull T defaultDeclaration) {
        T declaration = getDeclaration(gui, annotation, aClass);
        return declaration != null ? declaration : defaultDeclaration;
    }

    @ApiStatus.Internal
    protected final <T> @NotNull T getDeclarationOrThrow(final @NotNull Object gui, final @NotNull Class<? extends Annotation> annotation, final @NotNull Class<T> aClass) {
        T declaration = getDeclaration(gui, annotation, aClass);
        if (declaration == null) {
            throw new NullPointerException(String.format("Required declaration field @%s %s not found.", annotation.getName(), aClass.getName()));
        }
        return declaration;
    }
}
