package com.gorent.api.model.enums;

public enum PropertyStatus {
    FURNISHED("FURNISHED"),
    UNFURNISHED("UNFURNISHED");

    private final String label;

    PropertyStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
