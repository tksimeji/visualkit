package com.tksimeji.visualkit.type;

import com.tksimeji.visualkit.MerchantGui;
import com.tksimeji.visualkit.controller.MerchantGuiController;
import com.tksimeji.visualkit.controller.MerchantGuiControllerImpl;
import org.jetbrains.annotations.NotNull;

public final class MerchantGuiType implements GuiType<MerchantGui, MerchantGuiController> {
    private static final @NotNull MerchantGuiType instance = new MerchantGuiType();

    public static @NotNull MerchantGuiType instance() {
        return instance;
    }

    @Override
    public @NotNull Class<MerchantGui> getAnnotationClass() {
        return MerchantGui.class;
    }

    @Override
    public @NotNull Class<MerchantGuiController> getControllerClass() {
        return MerchantGuiController.class;
    }

    @Override
    public @NotNull MerchantGuiController createController(@NotNull Object gui, @NotNull MerchantGui annotation) {
        return new MerchantGuiControllerImpl(gui);
    }
}
