package com.tksimeji.visualkit.util;

import com.tksimeji.visualkit.api.Element;
import com.tksimeji.visualkit.api.Handler;
import com.tksimeji.visualkit.api.Asm;
import com.tksimeji.visualkit.api.Policy;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
@Deprecated(forRemoval = true)
public final class AsmUtility {
    public static @NotNull Set<Integer> of(@NotNull Element annotation) {
        throw new NotImplementedException();
    }

    public static @NotNull Set<Integer> of(@NotNull Policy annotation) {
        throw new NotImplementedException();
    }

    public static @NotNull Set<Integer> of(@NotNull Handler annotation) {
        throw new NotImplementedException();
    }

    public static @NotNull Set<Integer> of(@NotNull Asm... asms) {
        throw new NotImplementedException();
    }

    public static @NotNull Set<Integer> of(int... slots) {
        throw new NotImplementedException();    }

    public static @NotNull Set<Integer> range(int from, int to) {
        throw new NotImplementedException();
    }

    public static @NotNull Set<Integer> concat(@NotNull Set<Integer> i, @NotNull Set<Integer> j) {
        throw new NotImplementedException();
    }
}
