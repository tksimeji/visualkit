package com.tksimeji.visualkit.event.merchant;

import com.tksimeji.visualkit.event.EventImpl;
import com.tksimeji.visualkit.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiCloseEventImpl extends EventImpl implements MerchantGuiEvents.CloseEvent {
    public MerchantGuiCloseEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
