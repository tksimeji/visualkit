package com.tksimeji.kunectron.event;

import com.tksimeji.kunectron.Action;
import com.tksimeji.kunectron.Mouse;
import com.tksimeji.kunectron.element.ItemElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ItemContainerClickEventImpl extends GuiEventImpl implements ItemContainerClickEvent {
    protected final @Nullable ItemElement element;

    protected final int index;

    protected final @NotNull Action action;
    protected final @NotNull Mouse mouse;

    public ItemContainerClickEventImpl(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
        super(gui);

        this.index = index;
        this.element = element;
        this.action = action;
        this.mouse = mouse;

        ItemElement.Handler handler = element != null ? element.handler() : null;

        if (handler instanceof ItemElement.Handler1 handler1) {
            handler1.onClick();
        } else if (handler instanceof ItemElement.Handler2 handler2) {
            handler2.onClick(this);
        }
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
