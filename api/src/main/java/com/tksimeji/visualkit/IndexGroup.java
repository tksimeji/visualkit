package com.tksimeji.visualkit;

public @interface IndexGroup {
    int[] indexes() default {};
    int[] expectIndexes() default {};
    int indexFrom() default -1;
    int indexTo() default -1;
}
