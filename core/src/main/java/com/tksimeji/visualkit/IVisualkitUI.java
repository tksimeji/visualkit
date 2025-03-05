package com.tksimeji.visualkit;

import com.tksimeji.visualkit.controller.TickableController;
import com.tksimeji.visualkit.xmpl.XmplTarget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")public interface IVisualkitUI extends XmplTarget, TickableController {
    /**
     * Called every server tick.
     */
    default void onTick() {}

    /**
     * Bind a set of keys and values to the placeholder replacement map.
     *
     * @param key Key
     * @param value Value
     */
    void bind(@NotNull String key, @Nullable Object value);

    /**
     * Unbind an entry from the placeholder replacement map.
     *
     * @param key Key
     */
    void unbind(@NotNull String key);
}
