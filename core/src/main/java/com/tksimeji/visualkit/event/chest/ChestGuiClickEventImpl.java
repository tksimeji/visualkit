package com.tksimeji.visualkit.event.chest;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.event.ChestGuiEvents;
import com.tksimeji.visualkit.event.ItemContainerClickEventImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChestGuiClickEventImpl extends ItemContainerClickEventImpl implements ChestGuiEvents.ClickEvent {
    public ChestGuiClickEventImpl(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
        super(gui, index, element, action, mouse);
    }
}
