package com.gorent.api.model.enums;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String label;

    Gender(String label) {
        this.label = label;
    }
}
