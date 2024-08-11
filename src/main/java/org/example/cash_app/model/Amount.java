package org.example.cash_app.model;

import jakarta.validation.constraints.Min;

public class Amount {
    @Min(1)
    private long value;
    private String currency;

    public Amount() {
    }

    public long getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "сумма: " + value +
                ", валюта:'" + currency + "'";
    }
}
