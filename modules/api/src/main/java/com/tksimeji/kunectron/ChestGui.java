package com.tksimeji.kunectron;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ChestGui {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Player {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Title {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Size {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Element {
        int[] value() default {};
        IndexGroup[] groups() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Policy {
        int[] value() default {};
        boolean player() default false;
        IndexGroup[] groups() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface DefaultPolicy {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface PlayerDefaultPolicy {
    }

    enum ChestSize {
        SIZE_9(9),
        SIZE_18(18),
        SIZE_27(27),
        SIZE_36(36),
        SIZE_45(45),
        SIZE_54(54);

        private final int integer;

        ChestSize(int integer) {
            this.integer = integer;
        }

        public int toInt() {
            return integer;
        }
    }
}
