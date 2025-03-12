package com.tksimeji.kunectron.markupextension;

import org.jetbrains.annotations.NotNull;

public final class MarkupExtensionException extends RuntimeException {
    public MarkupExtensionException(final @NotNull String message) {
        super(message);
    }
}
