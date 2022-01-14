package com.gorent.api.model.enums;

public enum RentStatus {
    NEW_RENT("NEW_RENT"),
    PENDING("PENDING"),
    IN_RENT("IN_RENT"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String label;

    RentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
