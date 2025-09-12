package com.phoebe.pbsub.entity.enums;

/**
 * Report Type Enumeration
 */
public enum ReportType {
    TRADE_CONFIRM("Trade Confirmation"),
    OPTIONS_EXPIRY("Options Expiry"),
    MARGIN_CALL("Margin Call"),
    STATEMENT("Account Statement"),
    DAILY_PNL("Daily P&L");

    private final String description;

    ReportType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

