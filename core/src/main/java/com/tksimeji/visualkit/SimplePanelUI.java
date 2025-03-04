package com.tksimeji.visualkit;

import com.tksimeji.visualkit.xmpl.Xmpl;
import com.tksimeji.visualkit.util.ComponentUtility;
import com.tksimeji.visualkit.util.KillableArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")abstract class SimplePanelUI extends VisualkitUI implements IPanelUI {
    static final @NotNull ScoreboardManager sm = Bukkit.getScoreboardManager();

    private @NotNull Xmpl title = new Xmpl(Component.empty(), this);

    final @NotNull Scoreboard scoreboard = sm.getNewScoreboard();
    private final @NotNull Objective objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(), Criteria.DUMMY, title.asComponent());

    private final @NotNull List<Xmpl> lines = new KillableArrayList<>();

    private int blanks = 0;

    public SimplePanelUI() {
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @Override
    public final @Nullable Component getLine(int index) {
        if (index < 0 || size() <= index) {
            return null;
        }

        Xmpl line = lines.get(index);
        return line.asComponent();
    }

    @Override
    public final void setLine(int index, @NotNull Component line) {
        if (lines.size() <= index) {
            for (int i = size(); i <= index; i ++) {
                lines.add(new Xmpl(ComponentUtility.spaces(blanks ++), this));
            }
        }

        int blanks = this.blanks;

        TextComponent.Builder builder = Component.text().append(ComponentUtility.content(line).isBlank() ? ComponentUtility.spaces(blanks ++) : line);

        while (lines.stream().anyMatch(l -> ComponentUtility.equals(ComponentUtility.empty().append(builder.asComponent()), l.getSource()))) {
            builder.appendSpace();
        }

        if (ComponentUtility.serialize(ComponentUtility.empty().append(builder.build())).replaceAll("&.\\s*$", "")
                .equals(ComponentUtility.serialize(lines.get(index).getSource()).replaceAll("&.\\s*$", ""))) {
            return;
        }

        Optional.ofNullable(lines.get(index)).ifPresent(Xmpl::kill);

        this.blanks = blanks;

        lines.set(index, new Xmpl(builder.build(), this));
        push();
    }

    @Override
    public final void addLine(@NotNull Component line) {
        setLine(size(), line);
    }

    @Override
    public final void addLine(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < amount; i ++) {
            addLine();
        }
    }

    @Override
    public final void addLine() {
        addLine(Component.empty());
    }

    @Override
    public final void removeLine(int index) {
        lines.remove(index);
        push();
    }

    public @NotNull Component getTitle() {
        return objective.displayName();
    }

    public void setTitle(@NotNull Component title) {
        this.title.kill();
        this.title = new Xmpl(title, this);
    }

    private @Nullable Score getScore(int index) {
        int score = size() - index - 1;

        return scoreboard.getEntries().stream().filter(entry -> {
            Score s = objective.getScore(entry);
            return s.getScore() == score;
        }).findFirst().map(objective::getScore).orElse(null);
    }

    @Override
    public boolean isEmpty() {
        return lines.isEmpty();
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
    public final void tick() {
        super.tick();

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
}