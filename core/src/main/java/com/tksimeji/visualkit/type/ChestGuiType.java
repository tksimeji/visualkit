package com.tksimeji.visualkit.type;

import com.tksimeji.visualkit.ChestGui;
import com.tksimeji.visualkit.controller.ChestGuiController;
import com.tksimeji.visualkit.controller.ChestGuiControllerImpl;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

@Singleton
public final class ChestGuiType implements GuiType<ChestGui, ChestGuiController> {
    private static final @NotNull ChestGuiType instance = new ChestGuiType();

    public static @NotNull ChestGuiType instance() {
        return instance;
    }

    private ChestGuiType() {
    }

    @Override
    public @NotNull Class<ChestGui> getAnnotationClass() {
        return ChestGui.class;
    }

    @Override
    public @NotNull Class<ChestGuiController> getControllerClass() {
        return ChestGuiController.class;
    }

    @Override
    public @NotNull ChestGuiController createController(final @NotNull Object gui, final @NotNull ChestGui annotation) {
        return new ChestGuiControllerImpl(gui);
    }
}
