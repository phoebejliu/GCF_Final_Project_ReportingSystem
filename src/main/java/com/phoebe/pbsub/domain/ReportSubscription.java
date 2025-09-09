package com.phoebe.pbsub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Simplified Report Subscription Entity - Beginner Friendly
 */
@Entity
@Table(name = "report_subscriptions")
public class ReportSubscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associated with client - many-to-one relationship
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;

    // Report type - simple string, not using complex enums
    @NotBlank(message = "Report type cannot be empty")
    @Column(name = "report_type", nullable = false)
    private String reportType;

    // Frequency - simple string
    @NotBlank(message = "Frequency cannot be empty")
    @Column(nullable = false)
    private String frequency;

    // Format - simple string
    @NotBlank(message = "Format cannot be empty")
    @Column(nullable = false)
    private String format;

    // Delivery method - simplified to single string
    @NotBlank(message = "Delivery method cannot be empty")
    @Column(name = "delivery_method", nullable = false)
    private String deliveryMethod;


    // Constructors - beginner-friendly simple constructors
    public ReportSubscription() {}

    public ReportSubscription(Client client, String reportType, String frequency, 
                            String format, String deliveryMethod) {
        this.client = client;
        this.reportType = reportType;
        this.frequency = frequency;
        this.format = format;
        this.deliveryMethod = deliveryMethod;
    }

    // Getter and Setter methods - standard Java Bean pattern
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }


    // Simple toString method - for debugging
    @Override
    public String toString() {
        return "ReportSubscription{" +
                "id=" + id +
                ", reportType='" + reportType + '\'' +
                ", frequency='" + frequency + '\'' +
                ", format='" + format + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                '}';
    }
}

