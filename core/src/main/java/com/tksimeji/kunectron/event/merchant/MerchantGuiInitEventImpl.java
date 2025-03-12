package com.tksimeji.kunectron.event.merchant;

import com.tksimeji.kunectron.event.EventImpl;
import com.tksimeji.kunectron.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiInitEventImpl extends EventImpl implements MerchantGuiEvents.InitEvent {
    public MerchantGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
