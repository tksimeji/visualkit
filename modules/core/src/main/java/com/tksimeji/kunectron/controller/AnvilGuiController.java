package com.tksimeji.kunectron.controller;

import com.tksimeji.kunectron.element.ItemElement;
import org.bukkit.inventory.AnvilInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface AnvilGuiController extends ItemContainerGuiController<AnvilInventory> permits AnvilGuiControllerImpl {
    @NotNull String getText();

    @Nullable ItemElement getFirstElement();

    void setFirstElement(final @Nullable ItemElement element);

    @Nullable ItemElement getSecondElement();

    void setSecondElement(final @Nullable ItemElement element);

    @Nullable ItemElement getResultElement();

    void setResultElement(final @Nullable ItemElement element);

    boolean isOverwriteResultSlot();
}
