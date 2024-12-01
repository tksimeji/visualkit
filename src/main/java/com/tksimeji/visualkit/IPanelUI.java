package com.tksimeji.visualkit;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IPanelUI extends VisualkitUI, Killable {
    /**
     * Get any line.
     *
     * @param index Line index
     * @return Line
     */
    @Nullable Component get(int index);

    /**
     * Write on any line.
     *
     * @param index Line index
     * @param line Content
     */
    void set(int index, @NotNull Component line);

    /**
     * Add any line.
     *
     * @param line Content
     */
    void add(@NotNull Component line);

    /**
     * Add any number of blank lines.
     *
     * @param amount Number of lines
     */
    void add(int amount);

    /**
     * Add a blank line.
     */
    void add();

    /**
     * Remove any line.
     *
     * @param index Line index
     */
    void remove(int index);

    /**
     * Leave any line blank.
     *
     * @param index Line index
     */
    void clear(int index);

    /**
     * Delete all lines and initialize.
     */
    void clear();

    /**
     * Get the number of lines.
     *
     * @return Number of lines
     */
    int size();

    /**
     * Determine if the panel is empty.
     *
     * @return True if there are no lines
     */
    boolean empty();
}
