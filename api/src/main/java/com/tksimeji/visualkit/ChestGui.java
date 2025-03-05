package com.tksimeji.visualkit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ChestGui {
    Size size() default Size.SIZE_54;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Title {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Player {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Element {
        int[] value() default {};
        IndexGroup[] groups() default {};
    }

    @interface IndexGroup {
        int[] indexes() default {};
        int[] expectIndexes() default {};
        int indexFrom() default -1;
        int indexTo() default -1;
    }

    enum Size {
        SIZE_9(9),
        SIZE_18(18),
        SIZE_27(27),
        SIZE_36(36),
        SIZE_45(45),
        SIZE_54(54);

        private final int integer;

        Size(int integer) {
            this.integer = integer;
        }

        public int toInt() {
            return integer;
        }
    }
}
