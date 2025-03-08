package com.tksimeji.visualkit.controller;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public sealed interface ScoreboardGuiController extends GuiController permits ScoreboardGuiControllerImpl {
    @NotNull Set<Player> getPlayers();

    void addPlayer(final @NotNull Player player);

    void removePlayer(final @NotNull Player player);

    boolean isPlayer(final @NotNull Player player);

    @NotNull Component getTitle();

    void setTitle(final @NotNull ComponentLike title);

    @Nullable Component getLine(final int index);

    void setLine(final int index, final @NotNull ComponentLike line);

    void addLine(final @NotNull ComponentLike line);

    void removeLine(final int index);

    void removeLines();

    void insertLine(final int index, final @NotNull ComponentLike line);

    void clearLine(final int index);

    void clearLines();

    int getSize();
}
