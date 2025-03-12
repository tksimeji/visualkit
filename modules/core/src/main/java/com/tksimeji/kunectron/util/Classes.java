package com.tksimeji.kunectron.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Classes {
    public static @NotNull Set<Class<?>> getClassTree(final @NotNull Class<?> aClass) {
        Set<Class<?>> tree = new HashSet<>();
        tree.add(aClass);

        Class<?> superclass = aClass.getSuperclass();

        while (superclass != null) {
            tree.add(superclass);
            superclass = superclass.getSuperclass();
        }

        return tree;
    }

    public static @NotNull Set<Field> getFields(final @NotNull Class<?> aClass) {
        Set<Field> fields = new HashSet<>();
        getClassTree(aClass).forEach(clazz -> fields.addAll(Arrays.asList(clazz.getDeclaredFields())));
        return fields;
    }

    public static @NotNull Set<Method> getMethods(final @NotNull Class<?> aClass) {
        Set<Method> methods = new HashSet<>();
        getClassTree(aClass).forEach(clazz -> methods.addAll(Arrays.asList(clazz.getDeclaredMethods())));
        return methods;
    }
}
