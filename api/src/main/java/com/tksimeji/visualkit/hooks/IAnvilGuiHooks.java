package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import org.jetbrains.annotations.Nullable;

interface IAnvilGuiHooks extends ItemContainerGuiHooks {
    @Nullable ItemElement useGetFirstElement();

    void useSetFirstElement(final @Nullable ItemElement element);

    @Nullable ItemElement useGetSecondElement();

    void useSetSecondElement(final @Nullable ItemElement element);

    @Nullable ItemElement useGetResultElement();

    void useSetResultElement(final @Nullable ItemElement element);
}
