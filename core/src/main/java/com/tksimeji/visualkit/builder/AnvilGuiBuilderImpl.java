package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.AnvilGui;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.event.AnvilGuiEvents;
import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.event.Handler;
import com.tksimeji.visualkit.hooks.AnvilGuiHooks;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class AnvilGuiBuilderImpl extends ItemContainerGuiBuilderImpl<AnvilGuiBuilder, AnvilGuiHooks> implements AnvilGuiBuilder {
    @Override
    public @NotNull AnvilGuiBuilder firstElement(@NotNull ItemElement firstElement) {
        return element(0, firstElement);
    }

    @Override
    public @NotNull AnvilGuiBuilder secondElement(@NotNull ItemElement secondElement) {
        return element(1, secondElement);
    }

    @Override
    public @NotNull AnvilGuiBuilder resultElement(@NotNull ItemElement resultElement) {
        return element(2, resultElement);
    }

    @Override
    public @NotNull AnvilGuiHooks build(@NotNull Player player) {
        Gui gui = new Gui(player, title, elements.get(0), elements.get(1), elements.get(2), defaultPolicy, playerDefaultPolicy, policies, handlers);
        Visualkit.create(gui, AnvilGui.class);
        return gui;
    }

    @AnvilGui
    private static final class Gui extends AbstractGui implements AnvilGuiHooks {
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
                final @NotNull Map<Class<? extends Event>, HandlerFunction<?>> handlers
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

        @Handler
        public void onInit(final @NotNull AnvilGuiEvents.InitEvent event) {
            for (Map.Entry<Integer, ItemSlotPolicy> entry : policies.entrySet()) {
                useSetPolicy(entry.getKey(), entry.getValue());
            }
        }
    }
}
