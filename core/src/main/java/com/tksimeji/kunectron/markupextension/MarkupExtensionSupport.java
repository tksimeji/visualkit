package com.tksimeji.kunectron.markupextension;

import com.tksimeji.kunectron.markupextension.context.Context;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public interface MarkupExtensionSupport {
    @ApiStatus.Internal
    @Nullable Context<?> getContext();

    @ApiStatus.Internal
    void setContext(final @Nullable Context<?> ctx);
}
