package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ChestGuiHooks extends Hooks {
    default @NotNull ItemElement useElement(final @NotNull ItemType type) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default @Nullable ItemElement useGetElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default @NotNull Collection<ItemElement> useGetElements() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useSetElement(final int index, final @NotNull ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    default void useClearElement(final int index) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
