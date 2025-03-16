package com.tksimeji.kunectron.controller;

import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.ScoreboardGui;
import com.tksimeji.kunectron.controller.impl.GuiControllerImpl;
import com.tksimeji.kunectron.element.ComponentElement;
import com.tksimeji.kunectron.element.Element;
import com.tksimeji.kunectron.event.scoreboard.ScoreboardGuiInitEventImpl;
import com.tksimeji.kunectron.event.scoreboard.ScoreboardGuiPlayerAddEvent;
import com.tksimeji.kunectron.event.scoreboard.ScoreboardGuiPlayerRemoveEvent;
import com.tksimeji.kunectron.event.scoreboard.ScoreboardGuiTickEventImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ScoreboardGuiControllerImpl extends GuiControllerImpl implements ScoreboardGuiController {
    private static final @NotNull ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    private final @NotNull Scoreboard scoreboard = scoreboardManager.getNewScoreboard();;
    private final @NotNull Objective objective;

    private final @NotNull Set<Player> players = new HashSet<>();

    private @NotNull ComponentElement title;

    private final @NotNull List<ScoreboardLine> scoreboardLines = new ArrayList<>();

    public ScoreboardGuiControllerImpl(final @NotNull Object gui) {
        super(gui);

        title = Element.component(getDeclarationOrDefault(gui, ScoreboardGui.Title.class, ComponentLike.class, Component.empty()).getKey().asComponent(), markupExtensionContext);
        objective = scoreboard.registerNewObjective(UUID.randomUUID().toString(), Criteria.DUMMY, title.asComponent());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Map<Integer, ComponentLike> lineMap = new TreeMap<>();
        List<ComponentLike> lineList = new ArrayList<>();

        for (Pair<ComponentLike, ScoreboardGui.Line> declaration : getDeclarations(gui, ScoreboardGui.Line.class, ComponentLike.class)) {
            ScoreboardGui.Line annotation = declaration.getRight();
            if (annotation.index() != -1) {
                lineMap.put(annotation.index(), declaration.getLeft());
            } else {
                lineList.add(declaration.getLeft());
            }
        }

        for (Map.Entry<Integer, ComponentLike> line : lineMap.entrySet()) {
            setLine(line.getKey(), line.getValue());
        }

        for (ComponentLike line : lineList) {
            addLine(line);
        }
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

        Optional.ofNullable(ScoreboardGuiController.get(player)).ifPresent(oldController -> oldController.removePlayer(player));

        players.add(player);
        player.setScoreboard(scoreboard);
        callEvent(new ScoreboardGuiPlayerAddEvent(gui, player));
    }

    @Override
    public void removePlayer(final @NotNull Player player) {
        if (!isPlayer(player)) {
            return;
        }

        players.remove(player);
        player.setScoreboard(scoreboardManager.getMainScoreboard());
        callEvent(new ScoreboardGuiPlayerRemoveEvent(gui, player));
    }

    @Override
    public boolean isPlayer(final @NotNull Player player) {
        return players.contains(player);
    }

    @Override
    public @NotNull Component getTitle() {
        return title.asComponent();
    }

    @Override
    public void setTitle(final @NotNull ComponentLike title) {
        this.title = Element.component(title, markupExtensionContext);
        objective.displayName(this.title.asComponent());
    }

    @Override
    public @Nullable Component getLine(final int index) {
        if (index >= scoreboardLines.size()) {
            return null;
        }

        ScoreboardLine line = scoreboardLines.get(index);
        return line.getElement().create();
    }

    @Override
    public @NotNull List<Component> getLines() {
        return scoreboardLines.stream().map(line -> line.getElement().create()).toList();
    }

    @Override
    public void setLine(final int index, final @NotNull ComponentLike line) {
        ComponentElement element = Element.component(line.asComponent(), markupExtensionContext);

        if (index >= scoreboardLines.size()) {
            for (int i = scoreboardLines.size(); i <= index; i++) {
                ScoreboardLine scoreboardLine = new ScoreboardLineImpl();

                if (i == index) {
                    scoreboardLine.setElement(element);
                }

                scoreboardLines.add(scoreboardLine);
            }

            updateScores();
        } else {
            scoreboardLines.get(index).setElement(element);
        }
    }

    @Override
    public void addLine(final @NotNull ComponentLike line) {
        setLine(scoreboardLines.size(), line);
    }

    @Override
    public void removeLine(final int index) {
        if (index >= scoreboardLines.size()) {
            return;
        }
        scoreboardLines.get(index).remove();
    }

    @Override
    public void removeLines() {
        for (int i = 0; i < scoreboardLines.size(); i++) {
            removeLine(i);
        }
    }

    @Override
    public void insertLine(final int index, final @NotNull ComponentLike line) {
        scoreboardLines.set(index, new ScoreboardLineImpl(line));
        updateScores();
    }

    @Override
    public void clearLine(final int index) {
        if (index < 0 || index >= scoreboardLines.size()) {
            return;
        }
        setLine(index, Component.empty());
    }

    @Override
    public void clearLines() {
        for (int i = 0; i < scoreboardLines.size(); i++) {
            clearLine(i);
        }
    }

    @Override
    public int getSize() {
        return scoreboardLines.size();
    }

    @Override
    public void close() {
        Kunectron.removeGuiController(this);
        objective.unregister();
        for (Player player : getPlayers()) {
            removePlayer(player);
        }
    }

    @Override
    public void tick() {
        callEvent(new ScoreboardGuiTickEventImpl(gui));

        if (objective.displayName().equals(getTitle())) {
            objective.displayName(getTitle());
        }

        for (ScoreboardLine line : scoreboardLines) {
            if (!Objects.equals(line.getElement().create(), line.getRendered())) {
                line.render();
            }
        }
    }

    private void updateScores() {
        for (ScoreboardLine scoreboardLine : scoreboardLines) {
            scoreboardLine.updateScore();
        }
    }

    @ApiStatus.Internal
    private interface ScoreboardLine {
        int getIndex();

        @NotNull Score getScore();

        void updateScore();

        @NotNull ComponentElement getElement();

        void setElement(final @Nullable ComponentElement element);

        @Nullable Component getRendered();

        void render();

        void remove();
    }

    @ApiStatus.Internal
    private final class ScoreboardLineImpl implements ScoreboardLine {
        private @NotNull ComponentElement element;

        private final @NotNull Score score;

        private final @NotNull UUID uuid = UUID.randomUUID();

        public ScoreboardLineImpl() {
            this(null);
        }

        public ScoreboardLineImpl(final @Nullable ComponentLike component) {
            this(component != null ? Element.component(component) : null);
        }

        public ScoreboardLineImpl(final @Nullable ComponentElement element) {
            score = objective.getScore(uuid.toString());
            this.element = element != null ? element : Element.component();
            render();
        }

        @Override
        public int getIndex() {
            return scoreboardLines.indexOf(this);
        }

        @Override
        public @NotNull Score getScore() {
            return score;
        }

        @Override
        public void updateScore() {
            score.setScore(scoreboardLines.size() - 1 - getIndex());
        }

        @Override
        public @NotNull ComponentElement getElement() {
            return element;
        }

        @Override
        public void setElement(final @Nullable ComponentElement element) {
            this.element = element != null ? element : Element.component();
        }

        @Override
        public @Nullable Component getRendered() {
            return score.customName();
        }

        @Override
        public void render() {
            score.customName(element.create());
        }

        @Override
        public void remove() {
            scoreboard.resetScores(uuid.toString());
            scoreboardLines.remove(this);

            updateScores();
        }
    }
}
