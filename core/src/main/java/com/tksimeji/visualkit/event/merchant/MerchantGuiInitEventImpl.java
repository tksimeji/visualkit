package com.tksimeji.visualkit.event.merchant;

import com.tksimeji.visualkit.event.EventImpl;
import com.tksimeji.visualkit.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiInitEventImpl extends EventImpl implements MerchantGuiEvents.InitEvent {
    public MerchantGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
