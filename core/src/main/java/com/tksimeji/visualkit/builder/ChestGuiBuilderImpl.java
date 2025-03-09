package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.ChestGui;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.hooks.ChestGuiHooks;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public final class ChestGuiBuilderImpl extends ItemContainerGuiBuilderImpl<ChestGuiBuilder, ChestGuiHooks> implements ChestGuiBuilder {
    private @NotNull ChestGui.ChestSize size = ChestGui.ChestSize.SIZE_54;

    @Override
    public @NotNull ChestGuiBuilder size(final @NotNull ChestGui.ChestSize size) {
        this.size = size;
        return this;
    }

    @Override
    public @NotNull ChestGuiHooks build(final @NotNull Player player) {
        Gui gui = new Gui(player, title, size, handlers);

        Visualkit.create(gui, ChestGui.class);

        for (Map.Entry<Integer, ItemElement> entry : elements.entrySet()) {
            gui.useSetElement(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, ItemSlotPolicy> entry : policies.entrySet()) {
            gui.useSetPolicy(entry.getKey(), entry.getValue());
        }

        Optional.ofNullable(defaultPolicy).ifPresent(gui::useSetDefaultPolicy);
        Optional.ofNullable(playerDefaultPolicy).ifPresent(gui::useSetPlayerDefaultPolicy);
        return gui;
    }

    @ChestGui
    private static final class Gui extends AbstractGui implements ChestGuiHooks {
        @ChestGui.Player
        private final @NotNull Player player;

        @ChestGui.Title
        private final @NotNull Component title;

        @ChestGui.Size
        private final @NotNull ChestGui.ChestSize size;

        public Gui(final @NotNull Player player, final @NotNull ComponentLike title, final @NotNull ChestGui.ChestSize size, final @NotNull Map<Class<? extends Event>, HandlerFunction<?>> handlers) {
            super(handlers);
            
            this.player = player;
            this.title = title.asComponent();
            this.size = size;
        }
    }
}
