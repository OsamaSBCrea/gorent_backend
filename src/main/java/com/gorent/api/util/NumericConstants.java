package com.gorent.api.util;

public enum NumericConstants {
    LATEST_PROPERTIES(5);

    private final int value;

    NumericConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
