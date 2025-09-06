package com.phoebe.pbsub.domain.enums;

/**
 * Report Frequency Enumeration
 */
public enum Frequency {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly");

    private final String description;

    Frequency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

