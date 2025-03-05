package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChestGuiEvents {
    private ChestGuiEvents() {
    }

    public static final class ClickEvent extends AbstractClickEvent implements Cancellable {
        private static final HandlerList HANDLER_LIST = new HandlerList();

        public ClickEvent(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
            super(gui, index, element, action, mouse);
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }

    public static final class CloseEvent extends AbstractGuiEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        public CloseEvent(final @NotNull Object gui) {
            super(gui);
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }

    public static final class InitEvent extends AbstractGuiEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        public InitEvent(final @NotNull Object gui) {
            super(gui);
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }
}
