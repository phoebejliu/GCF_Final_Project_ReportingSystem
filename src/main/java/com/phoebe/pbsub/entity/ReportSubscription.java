package com.phoebe.pbsub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.phoebe.pbsub.entity.enums.ReportType;
import com.phoebe.pbsub.entity.enums.Frequency;
import com.phoebe.pbsub.entity.enums.ReportFormat;
import com.phoebe.pbsub.entity.enums.DeliveryMethod;

/**
 * Report Subscription Entity
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
    @NotNull(message = "Client cannot be null")
    private Client client;

    // Report type - using enum for type safety
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    @NotNull(message = "Report type cannot be null")
    private ReportType reportType;

    // Frequency - using enum for type safety
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Frequency cannot be null")
    private Frequency frequency;

    // Format - using enum for type safety
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Format cannot be null")
    private ReportFormat format;

    // Delivery method - using enum for type safety
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method", nullable = false)
    @NotNull(message = "Delivery method cannot be null")
    private DeliveryMethod deliveryMethod;


    // Constructors
    public ReportSubscription() {}

    public ReportSubscription(Client client, ReportType reportType, Frequency frequency, 
                            ReportFormat format, DeliveryMethod deliveryMethod) {
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

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public ReportFormat getFormat() {
        return format;
    }

    public void setFormat(ReportFormat format) {
        this.format = format;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }


    // toString method for debugging
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

