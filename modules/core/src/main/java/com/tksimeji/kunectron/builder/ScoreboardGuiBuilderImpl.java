package com.tksimeji.kunectron.builder;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.ScoreboardGui;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.hooks.ScoreboardGuiHooks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardGuiBuilderImpl extends IGuiBuilderImpl<ScoreboardGuiBuilder, ScoreboardGuiHooks> implements ScoreboardGuiBuilder {
    private @NotNull Component title = Component.empty();

    private final @NotNull List<Component> lines = new ArrayList<>();

    @Override
    public @NotNull ScoreboardGuiBuilder title(final @NotNull ComponentLike title) {
        Preconditions.checkArgument(title != null, "Title cannot be null.");
        this.title = title.asComponent();
        return this;
    }

    @Override
    public @NotNull ScoreboardGuiBuilder line(final @NotNull ComponentLike line) {
        Preconditions.checkArgument(line != null, "Line cannot be null.");
        lines.add(line.asComponent());
        return this;
    }

    @Override
    public @NotNull ScoreboardGuiHooks build() {
        Gui gui = new Gui(handlers);

        Kunectron.create(gui, ScoreboardGui.class);

        gui.useTitle(title);

        for (Component line : lines) {
            gui.useAddLine(line);
        }
        return gui;
    }

    @ScoreboardGui
    private static final class Gui extends AbstractGui<ScoreboardGuiHooks> implements ScoreboardGuiHooks {
        public Gui(final @NotNull List<HandlerInfo> handlers) {
            super(handlers);
        }
    }
}