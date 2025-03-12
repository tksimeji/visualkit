package com.tksimeji.kunectron.event.merchant;

import com.tksimeji.kunectron.element.TradeElement;
import com.tksimeji.kunectron.event.CancellableEventImpl;
import com.tksimeji.kunectron.event.MerchantGuiEvents;
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
