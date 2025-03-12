package com.tksimeji.kunectron.element;

import com.tksimeji.kunectron.controller.GuiController;
import org.jetbrains.annotations.NotNull;

public interface ObservableElement<T, C extends GuiController> extends Element<T> {
    void registerObserver(final @NotNull C observer);

    void unregisterObserver(final @NotNull C observer);
}
