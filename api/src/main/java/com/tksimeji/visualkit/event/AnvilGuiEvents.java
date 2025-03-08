package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AnvilGuiEvents {
    private AnvilGuiEvents() {
    }

    public static final class ClickEvent extends AbstractItemElementEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        public ClickEvent(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
            super(gui, index, element ,action, mouse);
        }

        public boolean isFirstSlot() {
            return getIndex() == 0;
        }

        public boolean isSecondSlot() {
            return getIndex() == 1;
        }

        public boolean isResultSlot() {
            return getIndex() == 2;
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }

    public static final class CloseEvent extends AbstractGuiEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        private final @NotNull String text;

        public CloseEvent(final @NotNull Object gui, final @NotNull String text) {
            super(gui);
            this.text = text;
        }

        @ApiStatus.Experimental
        public @NotNull String getText() {
            return text;
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

    @ApiStatus.Experimental
    public static final class TextChangeEvent extends AbstractGuiEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        private final @NotNull String text;

        public TextChangeEvent(final @NotNull Object gui, final @NotNull String text) {
            super(gui);
            this.text = text;
        }

        public @NotNull String getText() {
            return text;
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }
}
