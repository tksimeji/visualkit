package com.tksimeji.visualkit;

import com.tksimeji.visualkit.element.Xmpl;
import com.tksimeji.visualkit.util.CloseableArrayList;
import com.tksimeji.visualkit.util.ComponentUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class PanelUI implements IPanelUI {
    private static final @NotNull ScoreboardManager sm = Bukkit.getScoreboardManager();

    private @NotNull Xmpl title = new Xmpl(title(), this);

    private final @NotNull Scoreboard scoreboard = sm.getNewScoreboard();
    private final @NotNull Objective objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(), Criteria.DUMMY, title());
    private int blanks = 0;

    private final List<Xmpl> lines = new CloseableArrayList<>();
    private final Set<Player> players = new HashSet<>();

    public PanelUI() {
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Visualkit.sessions.add(this);
    }

    @Override
    public @NotNull List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public void addPlayer(@NotNull Player player) {
        IPanelUI old = Visualkit.getPanelUI(player);

        if (old != null) {
            old.removePlayer(player);
        }

        player.setScoreboard(scoreboard);
        players.add(player);
    }

    @Override
    public void removePlayer(@NotNull Player player) {
        if (! players.contains(player)) {
            return;
        }

        player.setScoreboard(sm.getMainScoreboard());
        players.remove(player);
    }

    @Override
    public @Nullable Component getLine(int index) {
        return lines.get(index).asComponent();
    }

    @Override
    public void setLine(int index, @NotNull Component line) {
        if (lines.size() <= index) {
            for (int i = lines.size(); i <= index; i ++) {
                lines.add(new Xmpl(ComponentUtility.space(blanks ++), this));
            }
        }

        if (ComponentUtility.equals(line, lines.get(index).getSource())) {
            return;
        }

        Xmpl old = lines.get(index);

        if (old != null) {
            old.close();
        }

        lines.set(index, new Xmpl(line, this));
        push();
    }

    @Override
    public void addLine(@NotNull Component line) {
        setLine(lines.size(), line);
    }

    @Override
    public void addLine(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < amount; i ++) {
            addLine();
        }
    }

    @Override
    public void addLine() {
        addLine(ComponentUtility.space(blanks ++));
    }

    @Override
    public void removeLine(int index) {
        lines.remove(index);
        push();
    }

    private @Nullable Score getScore(int index) {
        int score = size() - index - 1;

        return scoreboard.getEntries().stream().filter(entry -> {
            Score s = objective.getScore(entry);
            return s.getScore() == score;
        }).findFirst().map(objective::getScore).orElse(null);
    }

    private void push() {
        scoreboard.getEntries().forEach(scoreboard::resetScores);

        int i = 0;

        for (int j = size() - 1; 0 <= j; j --) {
            Score score = objective.getScore(LegacyComponentSerializer.legacySection().serialize(lines.get(i ++).asComponent()));
            score.setScore(j);
        }
    }

    @Override
    public int size() {
        return lines.size();
    }

    @Override
    public void clear() {
        lines.clear();
        blanks = 0;
        push();
    }

    @Override
    public final void tick() {
        onTick();

        if (! ComponentUtility.equals(title.getSource(), title())) {
            title.close();
            title = new Xmpl(title(), this);
        }

        if (! ComponentUtility.equals(objective.displayName(), title.asComponent())) {
            objective.displayName(title.asComponent());
        }

        for (int i = 0; i < size(); i ++) {
            Score score = getScore(i);

            if (score == null) {
                continue;
            }

            Xmpl line = lines.get(i);

            if (! ComponentUtility.equals(score.customName(), line.asComponent())) {
                score.customName(line.asComponent());
            }
        }
    }
}
