package com.tksimeji.kunectron.markupextension.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

class MutableContextImpl<T> extends ContextImpl<T> implements MutableContext<T> {
    private final @NotNull Map<String, Object> states = new HashMap<>();

    public MutableContextImpl(final @NotNull T object) {
        super(object);
    }

    public @Nullable Object getState(@NotNull String name) {
        if (states.containsKey(name)) {
            return states.get(name);
        }
        return super.getState(name);
    }

    @Override
    public void setState(@NotNull String name, @Nullable Object value) {
        states.put(name, value);
    }
}
