package com.tksimeji.visualkit;

import com.tksimeji.visualkit.xmpl.XmplTarget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IVisualkitUI extends XmplTarget, Tickable {
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
