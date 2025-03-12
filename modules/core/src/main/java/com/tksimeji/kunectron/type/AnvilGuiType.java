package com.tksimeji.kunectron.type;

import com.tksimeji.kunectron.AnvilGui;
import com.tksimeji.kunectron.controller.AnvilGuiController;
import com.tksimeji.kunectron.controller.AnvilGuiControllerImpl;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

@Singleton
public final class AnvilGuiType implements GuiType<AnvilGui, AnvilGuiController> {
    private static final @NotNull AnvilGuiType instance = new AnvilGuiType();

    public static @NotNull AnvilGuiType instance() {
        return instance;
    }

    private AnvilGuiType() {
    }

    @Override
    public @NotNull Class<AnvilGui> getAnnotationClass() {
        return AnvilGui.class;
    }

    @Override
    public @NotNull Class<AnvilGuiController> getControllerClass() {
        return AnvilGuiController.class;
    }

    @Override
    public @NotNull AnvilGuiController createController(final @NotNull Object gui, final @NotNull AnvilGui annotation) {
        return new AnvilGuiControllerImpl(gui, annotation);
    }
}
