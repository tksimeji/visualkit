package com.tksimeji.kunectron.event.merchant;

import com.tksimeji.kunectron.event.EventImpl;
import com.tksimeji.kunectron.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiCloseEventImpl extends EventImpl implements MerchantGuiEvents.CloseEvent {
    public MerchantGuiCloseEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
