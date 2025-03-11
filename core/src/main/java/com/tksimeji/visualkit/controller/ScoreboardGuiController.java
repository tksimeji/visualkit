package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.type.ScoreboardGuiType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public sealed interface ScoreboardGuiController extends GuiController, TickableController permits ScoreboardGuiControllerImpl {
    @ApiStatus.Internal
    static @Nullable ScoreboardGuiController get(final @NotNull Player player) {
        return Visualkit.getGuiControllers(ScoreboardGuiType.instance()).stream()
                .filter(controller -> controller.isPlayer(player))
                .findFirst()
                .orElse(null);
    }

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
