package com.gorent.api.model.enums;

public enum Currency {
    NIS("NIS"),
    JOD("JOD"),
    SAR("SAR"),
    BHR("BHR"),
    USD("USD"),
    EUR("EUR"),
    TRY("TRY");

    private final String label;

    Currency(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
