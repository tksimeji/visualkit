package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.controller.GuiController;
import org.jetbrains.annotations.NotNull;

public interface ObservableElement<T, C extends GuiController> extends Element<T> {
    void registerObserver(final @NotNull C observer);

    void unregisterObserver(final @NotNull C observer);
}
