package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
interface IAnvilGuiHooks extends ItemContainerGuiHooks {
    @Nullable ItemElement hookGetFirstElement();

    void hookSetFirstElement(final @Nullable ItemElement element);

    @Nullable ItemElement hookGetSecondElement();

    void hookSetSecondElement(final @Nullable ItemElement element);

    @Nullable ItemElement hookGetResultElement();

    void hookSetResultElement(final @Nullable ItemElement element);
}
