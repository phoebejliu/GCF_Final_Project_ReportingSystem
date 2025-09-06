package com.phoebe.pbsub.domain.enums;

/**
 * Delivery Method Enumeration
 */
public enum DeliveryMethod {
    EMAIL("Email Delivery"),
    UI("UI Display"),
    FTP("FTP Transfer");

    private final String description;

    DeliveryMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

