package com.tksimeji.visualkit;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface VisualkitUI extends Tickable {
    /**
     * Defines the UI title.
     *
     * @return UI title
     */
    @NotNull Component title();

    /**
     * Called every server tick.
     */
    default void onTick() {}
}
