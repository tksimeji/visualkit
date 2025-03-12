package com.tksimeji.kunectron.markupextension.context;

import com.tksimeji.kunectron.markupextension.MarkupExtensionException;
import com.tksimeji.kunectron.util.Classes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class ContextImpl<T> implements Context<T> {
    private static final @NotNull Map<Class<?>, Class<?>> wrapperClassMap = Map.of(
            boolean.class, Boolean.class,
            byte.class, Byte.class,
            char.class, Character.class,
            double.class, Double.class,
            float.class, Float.class,
            int.class, Integer.class,
            long.class, Long.class,
            short.class, Short.class
    );

    private static boolean areTypesCompatible(final @NotNull Class<?> requestedType, final @NotNull Class<?> actualType) {
        if (requestedType.equals(actualType)) {
            return true;
        }

        if (wrapperClassMap.get(requestedType) == actualType || wrapperClassMap.get(actualType) == requestedType) {
            return true;
        }

        return requestedType.isAssignableFrom(actualType) || actualType.isAssignableFrom(requestedType);
    }

    private final @NotNull T object;

    public ContextImpl(final @NotNull T object) {
        this.object = object;
    }

    @Override
    public @NotNull T getObject() {
        return object;
    }

    @Override
    public @Nullable Object getState(final @NotNull String name) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new MarkupExtensionException("State '" + name + "' not found.");
        }
    }

    @Override
    public @Nullable Object getFunction(final @NotNull String name, final @NotNull Object... args) {
        List<Class<?>> argTypes = Arrays.stream(args).map(arg -> arg != null ? arg.getClass() : Void.class).toList();

        try {
            Method method = Classes.getMethods(object.getClass()).stream()
                    .filter(aMethod -> aMethod.getName().equals(name))
                    .filter(aMethod -> aMethod.getParameterTypes().length == args.length)
                    .filter(aMethod -> {
                        for (int i = 0; i < aMethod.getParameterTypes().length; i++) {
                            if (!areTypesCompatible(aMethod.getParameterTypes()[i], argTypes.get(i))) {
                                return false;
                            }
                        }
                        return true;
                    })
                    .findFirst()
                    .orElse(null);

            if (method == null) {
                throw new MarkupExtensionException(String.format("Function '%s(%s)' not found.", name, String.join(", ", argTypes.stream().map(Class::getName).toList())));
            }

            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return new MarkupExtensionException(String.format("Failed to call '%s(%s)'.", name, String.join(", ", argTypes.stream().map(Class::getName).toList())));
        }
    }

    @Override
    public @Nullable Object getMember(final @NotNull String name) {
        try {
            return getState(name);
        } catch (RuntimeException e1) {
            try {
                return getFunction(name);
            } catch (RuntimeException e2) {
                throw new MarkupExtensionException("Member '" + name + "' not found.");
            }
        }
    }
}
