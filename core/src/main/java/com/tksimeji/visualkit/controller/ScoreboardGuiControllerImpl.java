package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.ScoreboardGui;
import com.tksimeji.visualkit.controller.impl.GuiControllerImpl;
import com.tksimeji.visualkit.event.scoreboard.ScoreboardGuiInitEventImpl;
import com.tksimeji.visualkit.util.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ScoreboardGuiControllerImpl extends GuiControllerImpl implements ScoreboardGuiController {
    private static final @NotNull ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    private final @NotNull Scoreboard scoreboard = scoreboardManager.getNewScoreboard();;
    private final @NotNull Objective objective;

    private final @NotNull Set<Player> players = new HashSet<>();

    private @NotNull Component title;

    private final @NotNull List<Component> lines = new ArrayList<>();

    private int spaces = 0;

    public ScoreboardGuiControllerImpl(final @NotNull Object gui) {
        super(gui);

        title = getDeclarationOrDefault(gui, ScoreboardGui.Title.class, ComponentLike.class, Component.empty()).getKey().asComponent();
        objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(), Criteria.DUMMY, title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @Override
    public void init() {
        callEvent(new ScoreboardGuiInitEventImpl(gui));
    }

    @Override
    public @NotNull Set<Player> getPlayers() {
        return new HashSet<>(players);
    }

    @Override
    public void addPlayer(final @NotNull Player player) {
        if (isPlayer(player)) {
            return;
        }

        players.add(player);
        player.setScoreboard(scoreboard);
    }

    @Override
    public void removePlayer(final @NotNull Player player) {
        if (! isPlayer(player)) {
            return;
        }

        players.remove(player);
        player.setScoreboard(scoreboardManager.getMainScoreboard());
    }

    @Override
    public boolean isPlayer(final @NotNull Player player) {
        return players.contains(player);
    }

    @Override
    public @NotNull Component getTitle() {
        return title;
    }

    @Override
    public void setTitle(final @NotNull ComponentLike title) {
        this.title = title.asComponent();
        objective.displayName(title.asComponent());
    }

    @Override
    public @Nullable Component getLine(final int index) {
        if (index >= lines.size()) {
            return Component.empty();
        }

        return lines.get(index);
    }

    @Override
    public void setLine(final int index, final @NotNull ComponentLike line) {
        if (index >=  lines.size()) {
            for (int i = lines.size(); i <= index; i ++) {
                lines.add(Components.spaces(spaces ++));
            }
        }

        lines.set(index, Components.plainText(line).isBlank() ? Components.spaces(spaces ++) : line.asComponent());
        update();
    }

    @Override
    public void addLine(final @NotNull ComponentLike line) {
        setLine(lines.size(), line);
    }

    @Override
    public void removeLine(final int index) {
        lines.remove(index);
        update();
    }

    @Override
    public void removeLines() {
        lines.clear();
        update();
    }

    @Override
    public void insertLine(final int index, final @NotNull ComponentLike line) {
        lines.add(index, line.asComponent());
        update();
    }

    @Override
    public void clearLine(final int index) {
        if (index < 0 || index >= lines.size()) {
            return;
        }

        setLine(index, Component.empty());
    }

    @Override
    public void clearLines() {
        for (int i = 0; i < lines.size(); i ++) {
            clearLine(i);
        }
    }

    @Override
    public int getSize() {
        return lines.size();
    }

    private void update() {
        scoreboard.getEntries().forEach(scoreboard::resetScores);

        int index = 0;
        for (int i = lines.size() - 1; 0 <= i; i --) {
            Score score = objective.getScore(Components.legacyComponent(Objects.requireNonNull(getLine(index++))));
            score.setScore(i);
        }
    }
}
