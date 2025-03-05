package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChestGuiEvents {
    private ChestGuiEvents() {
    }

    public static abstract class ChestGuiEvent extends Event implements GuiEvent {
        private final @NotNull Object gui;

        public ChestGuiEvent(final @NotNull Object gui) {
            this.gui = gui;
        }

        @Override
        public @NotNull Object getGui() {
            return gui;
        }
    }

    public static final class ClickEvent extends ChestGuiEvent implements Cancellable {
        private static final HandlerList HANDLER_LIST = new HandlerList();

        private final int index;

        private final @Nullable ItemElement element;

        private final @NotNull Action action;
        private final @NotNull Mouse mouse;

        private boolean cancelled = true;

        public ClickEvent(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
            super(gui);

            this.index = index;
            this.element = element;
            this.action = action;
            this.mouse = mouse;

            if (element != null && element.handler() != null) {
                ItemElement.Handler handler = element.handler();

                if (handler instanceof ItemElement.Handler1 handler1) {
                    handler1.onClick();
                } else if (handler instanceof ItemElement.Handler2 handler2) {
                    handler2.onClick(element);
                } else if (handler instanceof ItemElement.Handler3 handler3) {
                    handler3.onClick(index, action, mouse);
                } else if (handler instanceof ItemElement.Handler4 handler4) {
                    handler4.onClick(index, action, mouse, element);
                }
            }
        }

        public int getIndex() {
            return index;
        }

        public @Nullable ItemElement getElement() {
            return element;
        }

        public boolean hasElement() {
            return element != null;
        }

        public @NotNull Action getAction() {
            return action;
        }

        public boolean isSingleClick() {
            return action == Action.SINGLE_CLICK;
        }

        public boolean isDoubleClick() {
            return action == Action.DOUBLE_CLICK;
        }

        public boolean isShiftClick() {
            return action == Action.SHIFT_CLICK;
        }

        public @NotNull Mouse getMouse() {
            return mouse;
        }

        public boolean isLeft() {
            return mouse == Mouse.LEFT;
        }

        public boolean isRight() {
            return mouse == Mouse.RIGHT;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }

    public static final class CloseEvent extends ChestGuiEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        public CloseEvent(final @NotNull Object gui) {
            super(gui);
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }

    public static final class InitEvent extends ChestGuiEvent {
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
