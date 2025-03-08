package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.element.ItemElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ItemContainerClickEventImpl extends CancellableEventImpl implements ItemContainerClickEvent {
    protected final @Nullable ItemElement element;

    protected final int index;

    protected final @NotNull Action action;
    protected final @NotNull Mouse mouse;

    public ItemContainerClickEventImpl(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
        super(gui, true);

        this.index = index;
        this.element = element;
        this.action = action;
        this.mouse = mouse;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public @Nullable ItemElement getElement() {
        return element;
    }

    @Override
    public boolean hasElement() {
        return element != null;
    }

    @Override
    public @NotNull Action getAction() {
        return action;
    }

    @Override
    public boolean isSingleClick() {
        return action == Action.SINGLE_CLICK;
    }

    @Override
    public boolean isDoubleClick() {
        return action == Action.DOUBLE_CLICK;
    }

    @Override
    public boolean isShiftClick() {
        return action == Action.SHIFT_CLICK;
    }

    @Override
    public @NotNull Mouse getMouse() {
        return mouse;
    }

    @Override
    public boolean isLeftClick() {
        return mouse == Mouse.LEFT;
    }

    @Override
    public boolean isRightClick() {
        return mouse == Mouse.RIGHT;
    }
}
