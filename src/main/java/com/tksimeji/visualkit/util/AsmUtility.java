package com.tksimeji.visualkit.util;

import com.tksimeji.visualkit.api.InitialElement;
import com.tksimeji.visualkit.api.Handler;
import com.tksimeji.visualkit.api.Asm;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class AsmUtility {
    public static @NotNull Set<@NotNull Integer> of(@NotNull InitialElement annotation) {
        return concat(of(annotation.value()), of(annotation.asm()));
    }

    public static @NotNull Set<@NotNull Integer> of(@NotNull Handler annotation) {
        return concat(of(annotation.slot()), of(annotation.asm()));
    }

    public static @NotNull Set<@NotNull Integer> of(@NotNull Asm... asms) {
        Set<@NotNull Integer> set = new HashSet<>();

        for (Asm asm : asms) {
            set.addAll(concat(of(asm.value()), range(asm.from(), asm.to())));
        }

        return set;
    }

    public static @NotNull Set<@NotNull Integer> of(int... slots) {
        return Arrays.stream(slots).boxed().collect(Collectors.toSet());
    }

    public static @NotNull Set<@NotNull Integer> range(int from, int to) {
        if (to < from || from < 0) {
            return Set.of();
        }

        return of(IntStream.rangeClosed(from, to).toArray());
    }

    public static @NotNull Set<@NotNull Integer> concat(@NotNull Set<@NotNull Integer> i, @NotNull Set<@NotNull Integer> j) {
        return Stream.concat(i.stream(), j.stream()).collect(Collectors.toSet());
    }
}
