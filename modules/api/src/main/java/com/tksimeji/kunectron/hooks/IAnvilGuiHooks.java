package com.tksimeji.kunectron.hooks;

import com.tksimeji.kunectron.element.ItemElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
interface IAnvilGuiHooks extends ItemContainerGuiHooks {
    @Nullable ItemElement useFirstElement();

    void useFirstElement(final @Nullable ItemElement element);

    @Nullable ItemElement useSecondElement();

    void useSecondElement(final @Nullable ItemElement element);

    @Nullable ItemElement useResultElement();

    void useResultElement(final @Nullable ItemElement element);
}
