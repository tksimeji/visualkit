package com.tksimeji.kunectron.type;

import com.tksimeji.kunectron.MerchantGui;
import com.tksimeji.kunectron.controller.MerchantGuiController;
import com.tksimeji.kunectron.controller.MerchantGuiControllerImpl;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

@Singleton
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
    public @NotNull MerchantGuiController createController(final @NotNull Object gui, final @NotNull MerchantGui annotation) {
        return new MerchantGuiControllerImpl(gui);
    }
}
