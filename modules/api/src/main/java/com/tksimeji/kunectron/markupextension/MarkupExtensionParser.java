package com.tksimeji.kunectron.markupextension;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public interface MarkupExtensionParser extends IMarkupExtensionParser {
    static @NotNull MarkupExtensionParser create() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
