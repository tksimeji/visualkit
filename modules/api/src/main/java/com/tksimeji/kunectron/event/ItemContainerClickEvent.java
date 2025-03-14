package com.tksimeji.kunectron.event;

import com.tksimeji.kunectron.Action;
import com.tksimeji.kunectron.Mouse;
import com.tksimeji.kunectron.element.ItemElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemContainerClickEvent extends GuiEvent {
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
