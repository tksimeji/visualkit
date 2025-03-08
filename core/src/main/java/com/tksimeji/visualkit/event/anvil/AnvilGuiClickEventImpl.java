package com.tksimeji.visualkit.event.anvil;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.event.AnvilGuiEvents;
import com.tksimeji.visualkit.event.ItemContainerClickEventImpl;
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
