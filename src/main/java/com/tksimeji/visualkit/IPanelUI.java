package com.tksimeji.visualkit;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IPanelUI extends IVisualkitUI, Killable {
    /**
     * Get any line.
     *
     * @param index Line index
     * @return Line
     */
    @Nullable Component getLine(int index);

    /**
     * Write on any line.
     *
     * @param index Line index
     * @param line Content
     */
    void setLine(int index, @NotNull Component line);

    /**
     * Add any line.
     *
     * @param line Content
     */
    void addLine(@NotNull Component line);

    /**
     * Add any number of blank lines.
     *
     * @param amount Number of lines
     */
    void addLine(int amount);

    /**
     * Add a blank line.
     */
    void addLine();

    /**
     * Remove any line.
     *
     * @param index Line index
     */
    void removeLine(int index);

    /**
     * Get panel title.
     *
     * @return Title
     */
    @NotNull Component getTitle();

    /**
     * Set panel title.
     *
     * @param title New title
     */
    void setTitle(@NotNull Component title);

    /**
     * Determine if the panel is empty.
     *
     * @return True if there are no lines
     */
    boolean isEmpty();

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
}
