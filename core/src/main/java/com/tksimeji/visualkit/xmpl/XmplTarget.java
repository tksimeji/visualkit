package com.tksimeji.visualkit.xmpl;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
public interface XmplTarget {
    /**
     * Get the substitution map used by xmpl.
     *
     * @return Placeholder replacement map
     */
    @NotNull Map<String, Object> getXmplMap();
}
