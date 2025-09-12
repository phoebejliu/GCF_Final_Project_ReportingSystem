package com.phoebe.pbsub.entity.enums;

/**
 * Report Format Enumeration
 */
public enum ReportFormat {
    PDF("PDF Format"),
    CSV("CSV Format");

    private final String description;

    ReportFormat(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

