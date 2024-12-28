package com.tksimeji.visualkit.xmpl;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface XmplTarget {
    /**
     * Get the substitution map used by xmpl.
     *
     * @return Placeholder replacement map
     */
    @NotNull Map<String, Object> getXmplMap();
}
