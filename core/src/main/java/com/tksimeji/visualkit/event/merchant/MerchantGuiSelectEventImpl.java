package com.tksimeji.visualkit.event.merchant;

import com.tksimeji.visualkit.element.TradeElement;
import com.tksimeji.visualkit.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiSelectEventImpl extends MerchantGuiInteractEventImpl implements MerchantGuiEvents.SelectEvent {
    public MerchantGuiSelectEventImpl(final @NotNull Object gui, final int index, final @NotNull TradeElement element) {
        super(gui, index, element);

        if (element.selectHandler() instanceof TradeElement.SelectHandler1 handler) {
            handler.onSelect();
        } else if (element.selectHandler() instanceof TradeElement.SelectHandler2 handler) {
            handler.onSelect(this);
        }
    }
}
