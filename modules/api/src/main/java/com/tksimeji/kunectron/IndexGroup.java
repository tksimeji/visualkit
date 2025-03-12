package com.tksimeji.kunectron;

public @interface IndexGroup {
    int[] indexes() default {};
    int[] expectIndexes() default {};
    int indexFrom() default -1;
    int indexTo() default -1;
}
