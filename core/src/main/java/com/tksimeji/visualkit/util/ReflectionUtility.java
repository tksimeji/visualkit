package com.tksimeji.visualkit.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class ReflectionUtility {
    public static @NotNull Set<Class<?>> getClassTree(@NotNull Class<?> clazz) {
        Set<Class<?>> tree = new HashSet<>();
        tree.add(clazz);

        Class<?> superclass = clazz.getSuperclass();

        while (superclass != null) {
            tree.add(superclass);
            superclass = superclass.getSuperclass();
        }

        return tree;
    }

    public static @NotNull Set<Field> getFields(@NotNull Class<?> clazz) {
        Set<Field> fields = new HashSet<>();
        getClassTree(clazz).forEach(c -> fields.addAll(Arrays.asList(c.getDeclaredFields())));
        return fields;
    }

    public static @NotNull Set<Method> getMethods(@NotNull Class<?> clazz) {
        Set<Method> methods = new HashSet<>();
        getClassTree(clazz).forEach(c -> methods.addAll(Arrays.asList(c.getDeclaredMethods())));
        return methods;
    }
}
