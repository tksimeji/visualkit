package com.tksimeji.visualkit;

public interface VisualkitUI extends Tickable {
    /**
     * Called every server tick.
     */
    default void onTick() {}
}
