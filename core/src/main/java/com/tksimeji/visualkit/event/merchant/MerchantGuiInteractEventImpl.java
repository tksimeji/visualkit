package com.tksimeji.visualkit.event.merchant;

import com.tksimeji.visualkit.element.TradeElement;
import com.tksimeji.visualkit.event.CancellableEventImpl;
import com.tksimeji.visualkit.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public abstract class MerchantGuiInteractEventImpl extends CancellableEventImpl implements MerchantGuiEvents.InteractEvent {
    private final int index;

    private final @NotNull TradeElement element;

    public MerchantGuiInteractEventImpl(final @NotNull Object gui, final int index, final @NotNull TradeElement element) {
        super(gui);

        this.index = index;
        this.element = element;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull TradeElement getElement() {
        return element;
    }
}
