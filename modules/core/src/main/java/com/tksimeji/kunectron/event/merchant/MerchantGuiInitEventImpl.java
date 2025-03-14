package com.tksimeji.kunectron.event.merchant;

import com.tksimeji.kunectron.event.GuiEventImpl;
import com.tksimeji.kunectron.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiInitEventImpl extends GuiEventImpl implements MerchantGuiEvents.InitEvent {
    public MerchantGuiInitEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
