package com.tksimeji.visualkit.type;

import com.tksimeji.visualkit.ScoreboardGui;
import com.tksimeji.visualkit.controller.ScoreboardGuiController;
import com.tksimeji.visualkit.controller.ScoreboardGuiControllerImpl;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

@Singleton
public class ScoreboardGuiType implements GuiType<ScoreboardGui, ScoreboardGuiController> {
    private static final @NotNull ScoreboardGuiType instance = new ScoreboardGuiType();

    public static @NotNull ScoreboardGuiType instance() {
        return instance;
    }

    @Override
    public @NotNull Class<ScoreboardGui> getAnnotationClass() {
        return ScoreboardGui.class;
    }

    @Override
    public @NotNull Class<ScoreboardGuiController> getControllerClass() {
        return ScoreboardGuiController.class;
    }

    @Override
    public @NotNull ScoreboardGuiController createController(final @NotNull Object gui, final @NotNull ScoreboardGui annotation) {
        return new ScoreboardGuiControllerImpl(gui);
    }
}
