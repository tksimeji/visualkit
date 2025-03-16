package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.element.ItemElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@ApiStatus.Internal
interface IChestGuiHooks extends ItemContainerGuiHooks {
    @Nullable ItemElement useElement(final int index);

    void useElement(final int index, final @NotNull ItemElement element);

    void useClearElement(final int index);

    @NotNull Map<Integer, ItemElement> useElements();
}
