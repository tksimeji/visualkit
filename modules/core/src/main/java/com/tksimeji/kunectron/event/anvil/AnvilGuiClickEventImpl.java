package com.tksimeji.kunectron.event.anvil;

import com.tksimeji.kunectron.Action;
import com.tksimeji.kunectron.Mouse;
import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.event.AnvilGuiEvents;
import com.tksimeji.kunectron.event.ItemContainerClickEventImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AnvilGuiClickEventImpl extends ItemContainerClickEventImpl implements AnvilGuiEvents.ClickEvent {
    public AnvilGuiClickEventImpl(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
        super(gui, index, element, action, mouse);
    }

    @Override
    public boolean isFirstSlot() {
        return index == 0;
    }

    @Override
    public boolean isSecondSlot() {
        return index == 1;
    }

    @Override
    public boolean isResultSlot() {
        return index == 2;
    }
}
