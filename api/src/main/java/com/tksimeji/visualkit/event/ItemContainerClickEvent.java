package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.element.ItemElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemContainerClickEvent extends Event {
    int getIndex();

    @Nullable ItemElement getElement();

    boolean hasElement();

    @NotNull Action getAction();

    boolean isSingleClick();

    boolean isDoubleClick();

    boolean isShiftClick();

    @NotNull Mouse getMouse();

    boolean isLeftClick();

    boolean isRightClick();
}
