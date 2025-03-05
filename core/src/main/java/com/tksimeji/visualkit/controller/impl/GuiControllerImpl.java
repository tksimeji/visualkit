package com.tksimeji.visualkit.controller.impl;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.event.GuiHandler;
import com.tksimeji.visualkit.event.GuiEvent;
import com.tksimeji.visualkit.util.ReflectionUtility;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class GuiControllerImpl implements GuiController {
    protected final @NotNull Object gui;

    protected final @NotNull Set<Method> handlers = new LinkedHashSet<>();

    public GuiControllerImpl(final @NotNull Object gui) {
        this.gui = gui;

        Visualkit.addGuiController(this);

        ReflectionUtility.getMethods(gui.getClass()).stream()
                .filter(method -> method.isAnnotationPresent(GuiHandler.class) &&
                        method.getParameters().length == 1 &&
                        Event.class.isAssignableFrom(method.getParameterTypes()[0]))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(GuiHandler.class).priority()))
                .forEach(handlers::add);
    }

    @Override
    public @NotNull Object getGui() {
        return gui;
    }

    @Override
    public boolean callEvent(final @NotNull GuiEvent event) {
        List<Method> handlers = this.handlers.stream().filter(handler -> event.getClass().isAssignableFrom(handler.getParameterTypes()[0])).toList();

        for (Method handler : handlers) {
            if (event instanceof Cancellable && ((Cancellable) event).isCancelled() && handler.getAnnotation(GuiHandler.class).ignoreCancelled()) {
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
    protected final  <A extends Annotation, T> @Nullable Pair<T, A> getDeclaration(final @NotNull Object gui, final @NotNull Class<A> annotation, final @NotNull Class<T> aClass) {
        return getDeclarations(gui, annotation, aClass).stream().findFirst().orElse(null);
    }

    @ApiStatus.Internal
    protected final  <A extends Annotation, T> @NotNull Pair<T, A> getDeclarationOrDefault(final @NotNull Object gui, final @NotNull Class<A> annotation, final @NotNull Class<T> aClass, final @NotNull T defaultValue) {
        Pair<T, A> pair = getDeclaration(gui, annotation, aClass);
        return pair != null ? pair : Pair.of(defaultValue, null);
    }

    @ApiStatus.Internal
    protected final <A extends Annotation, T> @NotNull Pair<T, A> getDeclarationOrThrow(final @NotNull Object gui, final @NotNull Class<A> annotation, final @NotNull Class<T> aClass) {
        Pair<T, A> pair = getDeclaration(gui, annotation, aClass);
        if (pair == null) {
            throw new NullPointerException(String.format("Required declaration field @%s %s not found.", annotation.getName(), aClass.getName()));
        }
        return pair;
    }

    @ApiStatus.Internal
    protected final <A extends Annotation, T> @NotNull Set<Pair<T, A>> getDeclarations(final @NotNull Object gui, final @NotNull Class<A> annotation, final @NotNull Class<T> aClass) {
        return ReflectionUtility.getFields(gui.getClass()).stream()
                .filter(field -> field.isAnnotationPresent(annotation) && aClass.isAssignableFrom(field.getType()))
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return Pair.of((T) field.get(gui), field.getAnnotation(annotation));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

}
