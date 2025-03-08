package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.element.TradeElement;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiEvents {
    private MerchantGuiEvents() {
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

    private static abstract class MerchantElementGuiEvent extends AbstractGuiEvent implements Cancellable {
        private final int index;

        private final @NotNull TradeElement element;

        private boolean cancelled;

        public MerchantElementGuiEvent(final @NotNull Object gui, final int index, final @NotNull TradeElement element) {
            super(gui);
            this.index = index;
            this.element = element;
        }

        public int getIndex() {
            return index;
        }

        public @NotNull TradeElement getElement() {
            return element;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(final boolean cancelled) {
            this.cancelled = cancelled;
        }
    }

    public static final class SelectEvent extends MerchantElementGuiEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        public SelectEvent(final @NotNull Object gui, final int index, final @NotNull TradeElement element) {
            super(gui, index, element);

            TradeElement.SelectHandler handler = element.selectHandler();

            if (handler != null) {
                handler.onSelect();
            }
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }

    public static final class PurchaseEvent extends MerchantElementGuiEvent {
        private static final @NotNull HandlerList HANDLER_LIST = new HandlerList();

        public PurchaseEvent(final @NotNull Object gui, final int index, final @NotNull TradeElement element) {
            super(gui, index, element);

            TradeElement.PurchaseHandler handler = element.purchaseHandler();

            if (handler != null) {
                handler.onPurchase();
            }
        }

        @Override
        public @NotNull HandlerList getHandlers() {
            return HANDLER_LIST;
        }
    }
}
