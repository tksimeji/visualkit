package com.tksimeji.visualkit;

import com.tksimeji.visualkit.element.Xmpl;
import com.tksimeji.visualkit.util.ComponentUtility;
import com.tksimeji.visualkit.util.KillableArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

abstract class SimplePanelUI implements IPanelUI {
    static final @NotNull ScoreboardManager sm = Bukkit.getScoreboardManager();

    private @NotNull Xmpl title = new Xmpl(title(), this);

    final @NotNull Scoreboard scoreboard = sm.getNewScoreboard();
    private final @NotNull Objective objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(), Criteria.DUMMY, title());

    private final @NotNull List<@NotNull Xmpl> lines = new KillableArrayList<>();

    private int blanks = 0;

    public SimplePanelUI() {
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Visualkit.sessions.add(this);
    }

    @Override
    public final @Nullable Component get(int index) {
        if (index < 0 || size() <= index) {
            return null;
        }

        Xmpl line = lines.get(index);
        return line.asComponent();
    }

    @Override
    public final void set(int index, @NotNull Component line) {
        if (lines.size() <= index) {
            for (int i = size(); i <= index; i ++) {
                lines.add(new Xmpl(ComponentUtility.space(blanks ++), this));
            }
        }

        if (ComponentUtility.equals(line, lines.get(index).getSource())) {
            return;
        }

        Optional.ofNullable(lines.get(index)).ifPresent(Xmpl::kill);

        lines.set(index, new Xmpl(line, this));
        push();
    }

    @Override
    public final void add(@NotNull Component line) {
        set(size(), line);
    }

    @Override
    public final void add(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < amount; i ++) {
            add();
        }
    }

    @Override
    public final void add() {
        add(ComponentUtility.space(blanks ++));
    }

    @Override
    public final void remove(int index) {
        lines.remove(index);
        push();
    }

    @Override
    public final void clear(int index) {
        if (index < 0 || size() <= index) {
            return;
        }

        set(index, Component.empty());
    }

    @Override
    public final void clear() {
        lines.clear();
        blanks = 0;
        push();
    }

    @Override
    public final int size() {
        return lines.size();
    }

    @Override
    public boolean empty() {
        return lines.isEmpty();
    }

    @Override
    public final void tick() {
        onTick();

        if (! ComponentUtility.equals(title.getSource(), title())) {
            title.kill();
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

    @Override
    public void kill() {
        title.kill();
        lines.clear();
        Visualkit.sessions.remove(this);
    }

    private void push() {
        scoreboard.getEntries().forEach(scoreboard::resetScores);

        int i = 0;

        for (int j = size() - 1; 0 <= j; j --) {
            Score score = objective.getScore(LegacyComponentSerializer.legacySection().serialize(lines.get(i ++).asComponent()));
            score.setScore(j);
        }
    }

    private @Nullable Score getScore(int index) {
        int score = size() - index - 1;

        return scoreboard.getEntries().stream().filter(entry -> {
            Score s = objective.getScore(entry);
            return s.getScore() == score;
        }).findFirst().map(objective::getScore).orElse(null);
    }
}
