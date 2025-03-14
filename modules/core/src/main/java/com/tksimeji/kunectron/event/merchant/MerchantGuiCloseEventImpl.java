package com.tksimeji.kunectron.event.merchant;

import com.tksimeji.kunectron.event.GuiEventImpl;
import com.tksimeji.kunectron.event.MerchantGuiEvents;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiCloseEventImpl extends GuiEventImpl implements MerchantGuiEvents.CloseEvent {
    public MerchantGuiCloseEventImpl(final @NotNull Object gui) {
        super(gui);
    }
}
