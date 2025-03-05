package com.tksimeji.visualkit.event;

import com.tksimeji.visualkit.Action;
import com.tksimeji.visualkit.Mouse;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.GuiController;
import com.tksimeji.visualkit.controller.InventoryGuiController;
import com.tksimeji.visualkit.element.ItemElement;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractClickEvent extends AbstractGuiEvent implements Cancellable, GuiEvent {
    private final int index;

    private final @Nullable ItemElement element;

    private final @NotNull Action action;
    private final @NotNull Mouse mouse;

    private boolean cancelled = true;

    public AbstractClickEvent(final @NotNull Object gui, final int index, final @Nullable ItemElement element, final @NotNull Action action, final @NotNull Mouse mouse) {
        super(gui);

        this.index = index;
        this.element = element;
        this.action = action;
        this.mouse = mouse;

        if (element == null) {
            return;
        }

        if (element.sound() != null) {
            GuiController controller = Visualkit.getGuiController(gui);

            if (controller instanceof InventoryGuiController inventoryGuiController) {
                Player player = inventoryGuiController.getPlayer();
                player.playSound(player, element.sound(), element.soundVolume(), element.soundPitch());
            }
        }

        if (element.handler() != null) {
            ItemElement.Handler handler = element.handler();

            if (handler instanceof ItemElement.Handler1 handler1) {
                handler1.onClick();
            } else if (handler instanceof ItemElement.Handler2 handler2) {
                handler2.onClick(element);
            } else if (handler instanceof ItemElement.Handler3 handler3) {
                handler3.onClick(index, action, mouse);
            } else if (handler instanceof ItemElement.Handler4 handler4) {
                handler4.onClick(index, action, mouse, element);
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public @Nullable ItemElement getElement() {
        return element;
    }

    public boolean hasElement() {
        return element != null;
    }

    public @NotNull Action getAction() {
        return action;
    }

    public boolean isSingleClick() {
        return action == Action.SINGLE_CLICK;
    }

    public boolean isDoubleClick() {
        return action == Action.DOUBLE_CLICK;
    }

    public boolean isShiftClick() {
        return action == Action.SHIFT_CLICK;
    }

    public @NotNull Mouse getMouse() {
        return mouse;
    }

    public boolean isLeft() {
        return mouse == Mouse.LEFT;
    }

    public boolean isRight() {
        return mouse == Mouse.RIGHT;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
