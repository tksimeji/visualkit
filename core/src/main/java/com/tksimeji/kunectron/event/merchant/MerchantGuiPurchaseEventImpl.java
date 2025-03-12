package com.tksimeji.kunectron.event.merchant;

import com.tksimeji.kunectron.element.TradeElement;
import com.tksimeji.kunectron.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiPurchaseEventImpl extends MerchantGuiInteractEventImpl implements MerchantGuiEvents.PurchaseEvent {
    public MerchantGuiPurchaseEventImpl(final @NotNull Object gui, final int index, final @NotNull TradeElement element) {
        super(gui, index, element);

        if (element.purchaseHandler() instanceof TradeElement.PurchaseHandler1 handler) {
            handler.onPurchase();
        } else if (element.purchaseHandler() instanceof TradeElement.PurchaseHandler2 handler) {
            handler.onPurchase(this);
        }
    }
}
