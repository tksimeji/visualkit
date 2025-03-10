package com.tksimeji.visualkit.markupextension;

import org.jetbrains.annotations.NotNull;

public interface MarkupExtensionParser extends IMarkupExtensionParser {
    static @NotNull MarkupExtensionParser create() {
        return new MarkupExtensionParserImpl();
    }
}
