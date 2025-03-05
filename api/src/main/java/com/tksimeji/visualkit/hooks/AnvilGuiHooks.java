package com.tksimeji.visualkit.hooks;

import com.tksimeji.visualkit.element.ItemElement;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public interface AnvilGuiHooks extends IAnvilGuiHooks {
    @Override
    default @Nullable ItemElement useGetFirstElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetFirstElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement useGetSecondElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetSecondElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default @Nullable ItemElement useGetResultElement() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    @Override
    default void useSetResultElement(@Nullable ItemElement element) {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
