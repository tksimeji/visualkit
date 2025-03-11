package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.ScoreboardGui;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.hooks.ScoreboardGuiHooks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreboardGuiBuilderImpl extends IGuiBuilderImpl<ScoreboardGuiBuilder, ScoreboardGuiHooks> implements ScoreboardGuiBuilder {
    private @NotNull Component title = Component.empty();

    private final @NotNull List<Component> lines = new ArrayList<>();

    @Override
    public @NotNull ScoreboardGuiBuilder title(final @NotNull ComponentLike title) {
        this.title = title.asComponent();
        return this;
    }

    @Override
    public @NotNull ScoreboardGuiBuilder line(final @NotNull ComponentLike line) {
        lines.add(line.asComponent());
        return this;
    }

    @Override
    public @NotNull ScoreboardGuiHooks build() {
        Gui gui = new Gui(handlers);

        Visualkit.create(gui, ScoreboardGui.class);

        gui.hookSetTitle(title);

        for (Component line : lines) {
            gui.hookAddLine(line);
        }
        return gui;
    }

    @ScoreboardGui
    private static final class Gui extends AbstractGui implements ScoreboardGuiHooks {
        public Gui(final @NotNull Map<Class<? extends Event>, HandlerFunction<?>> handlers) {
            super(handlers);
        }
    }
}
