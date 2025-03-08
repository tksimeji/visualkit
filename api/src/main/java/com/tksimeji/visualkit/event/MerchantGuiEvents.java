package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.element.TradeElement;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiEvents {
    private MerchantGuiEvents() {
        throw new UnsupportedOperationException();
    }

    public interface CloseEvent extends Event {
    }

    public interface InitEvent extends Event {
    }

    public interface InteractEvent extends CancellableEvent {
        int getIndex();

        @NotNull TradeElement getElement();
    }

    public interface SelectEvent extends InteractEvent {
    }

    public interface PurchaseEvent extends InteractEvent {
    }
}
