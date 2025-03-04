package com.tksimeji.visualkit;

import com.tksimeji.visualkit.util.ReflectionUtility;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")public abstract class VisualkitUI implements IVisualkitUI {
    private final @NotNull Map<String, Object> xmplMap = new HashMap<>();

    public VisualkitUI() {
        Visualkit.sessions.add(this);
    }

    @Override
    public final @NotNull Map<String, Object> getXmplMap() {
        ReflectionUtility.getFields(getClass()).stream()
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        bind(field.getName(), field.get(this));
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(e);
                    }
                });

        return xmplMap;
    }

    @Override
    public final void bind(@NotNull String key, @Nullable Object value) {
        xmplMap.put(key, value);
    }

    @Override
    public final void unbind(@NotNull String key) {
        xmplMap.remove(key);
    }

    @Override
    public void tick() {
        onTick();
    }
}
