package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.MerchantGui;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.element.TradeElement;
import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.hooks.MerchantGuiHooks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class MerchantGuiBuilderImpl extends ContainerGuiBuilderImpl<MerchantGuiBuilder, MerchantGuiHooks> implements MerchantGuiBuilder {
    private final @NotNull List<TradeElement> elements = new ArrayList<>();

    @Override
    public @NotNull MerchantGuiBuilder element(final @NotNull TradeElement element) {
        elements.add(element);
        return this;
    }

    @Override
    public @NotNull MerchantGuiHooks build(final @NotNull Player player) {
        Gui gui = new Gui(player, title, handlers);

        Visualkit.create(gui, MerchantGui.class);

        for (TradeElement element : elements) {
            gui.hookAddElement(element);
        }
        return gui;
    }

    @MerchantGui
    private static final class Gui extends AbstractGui implements MerchantGuiHooks {
        @MerchantGui.Player
        private final @NotNull Player player;

        @MerchantGui.Title
        private final @NotNull Component title;

        public Gui(final @NotNull Player player, final @NotNull ComponentLike title, final @NotNull Map<Class<? extends Event>, HandlerFunction<?>> handlers) {
            super(handlers);

            this.player = player;
            this.title = title.asComponent();
        }
    }
}
