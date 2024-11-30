package com.tksimeji.visualkit;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IPanelUI extends VisualkitUI {
    @NotNull List<Player> getPlayers();

    void addPlayer(@NotNull Player player);

    void removePlayer(@NotNull Player player);

    @Nullable Component getLine(int index);

    void setLine(int index, @NotNull Component line);

    void addLine(@NotNull Component line);

    void addLine(int amount);

    void addLine();

    void removeLine(int index);

    default void onTick() {}

    void clear();

    int size();
}
