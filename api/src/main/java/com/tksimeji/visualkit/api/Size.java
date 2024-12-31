package com.tksimeji.visualkit.api;

public enum Size {
    SIZE_9(9),
    SIZE_18(18),
    SIZE_27(27),
    SIZE_36(36),
    SIZE_45(45),
    SIZE_54(54);

    private final int i;

    Size(int i) {
        this.i = i;
    }

    public int asInt() {
        return i;
    }
}
