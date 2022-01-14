package com.gorent.api.dto;

import com.gorent.api.model.enums.Currency;

public class CountryDTO {

    private String name;

    private String countryCode;

    private Currency currency;

    public CountryDTO() {
    }

    public CountryDTO(String name, String countryCode, Currency currency) {
        this.name = name;
        this.countryCode = countryCode;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
