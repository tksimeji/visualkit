package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@ApiStatus.Internal
interface IChestGuiHooks extends ItemContainerGuiHooks {
    @Nullable ItemElement useGetElement(final int index);

    @NotNull Map<Integer, ItemElement> useGetElements();

    void useSetElement(final int index, final @NotNull ItemElement element);

    void useClearElement(final int index);
}
