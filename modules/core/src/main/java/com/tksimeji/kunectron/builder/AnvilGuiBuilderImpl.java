package com.tksimeji.kunectron.builder;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.AnvilGui;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.event.AnvilGuiEvents;
import com.tksimeji.kunectron.event.GuiHandler;
import com.tksimeji.kunectron.hooks.AnvilGuiHooks;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public final class AnvilGuiBuilderImpl extends ItemContainerGuiBuilderImpl<AnvilGuiBuilder, AnvilGuiHooks> implements AnvilGuiBuilder {
    @Override
    public @NotNull AnvilGuiBuilder firstElement(@NotNull ItemElement firstElement) {
        Preconditions.checkArgument(firstElement != null, "First element cannot be null.");
        return element(0, firstElement);
    }

    @Override
    public @NotNull AnvilGuiBuilder secondElement(@NotNull ItemElement secondElement) {
        Preconditions.checkArgument(secondElement != null, "Second element cannot be null.");
        return element(1, secondElement);
    }

    @Override
    public @NotNull AnvilGuiBuilder resultElement(@NotNull ItemElement resultElement) {
        Preconditions.checkArgument(resultElement != null, "Result element cannot be null.");
        return element(2, resultElement);
    }

    @Override
    public @NotNull AnvilGuiHooks build(@NotNull Player player) {
        Preconditions.checkArgument(player != null, "Player cannot be null.");
        Gui gui = new Gui(player, title, elements.get(0), elements.get(1), elements.get(2), defaultPolicy, playerDefaultPolicy, policies, handlers);
        Kunectron.create(gui, AnvilGui.class);
        return gui;
    }

    @AnvilGui
    private static final class Gui extends AbstractGui<AnvilGuiHooks> implements AnvilGuiHooks {
        @AnvilGui.Player
        private final @NotNull Player player;

        @AnvilGui.Title
        private final @NotNull Component title;

        @AnvilGui.FirstElement
        private final @Nullable ItemElement firstElement;

        @AnvilGui.SecondElement
        private final @Nullable ItemElement secondElement;

        @AnvilGui.ResultElement
        private final @Nullable ItemElement resultElement;

        @AnvilGui.DefaultPolicy
        private final @Nullable ItemSlotPolicy defaultPolicy;

        @AnvilGui.PlayerDefaultPolicy
        private final @Nullable ItemSlotPolicy playerDefaultPolicy;

        private final @NotNull Map<Integer, ItemSlotPolicy> policies;

        public Gui(
                final @NotNull Player player,
                final @NotNull ComponentLike title,
                final @Nullable ItemElement firstElement,
                final @Nullable ItemElement secondElement,
                final @Nullable ItemElement resultElement,
                final @Nullable ItemSlotPolicy defaultPolicy,
                final @Nullable ItemSlotPolicy playerDefaultPolicy,
                final @NotNull Map<Integer, ItemSlotPolicy> policies,
                final @NotNull List<HandlerInfo> handlers
        ) {
            super(handlers);

            this.player = player;
            this.title = title.asComponent();
            this.firstElement = firstElement;
            this.secondElement = secondElement;
            this.resultElement = resultElement;
            this.defaultPolicy = defaultPolicy;
            this.playerDefaultPolicy = playerDefaultPolicy;
            this.policies = policies;
        }

        @GuiHandler
        public void onInit(final @NotNull AnvilGuiEvents.InitEvent event) {
            for (Map.Entry<Integer, ItemSlotPolicy> entry : policies.entrySet()) {
                this.usePolicy(entry.getKey(), entry.getValue());
            }
        }
    }
}