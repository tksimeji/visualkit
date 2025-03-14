package com.tksimeji.kunectron.event;

import com.tksimeji.kunectron.element.TradeElement;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiEvents {
    private MerchantGuiEvents() {
        throw new UnsupportedOperationException();
    }

    public interface CloseEvent extends GuiEvent {
    }

    public interface InitEvent extends GuiEvent {
    }

    public interface InteractEvent extends CancellableEvent {
        int getIndex();

        @NotNull TradeElement getElement();
    }

    public interface PurchaseEvent extends InteractEvent {
    }

    public interface SelectEvent extends InteractEvent {
    }
}
