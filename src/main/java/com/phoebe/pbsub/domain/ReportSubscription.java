package com.phoebe.pbsub.domain;

import com.phoebe.pbsub.domain.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.HashSet;
import java.util.Set;

/**
 * Report Subscription Entity Class
 */
@Entity
@Table(name = "report_subscriptions")
public class ReportSubscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;

    @NotNull(message = "Report type cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @NotNull(message = "Frequency cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;

    @NotNull(message = "Format cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportFormat format;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "subscription_delivery_methods", joinColumns = @JoinColumn(name = "subscription_id"))
    @Column(name = "delivery_method")
    private Set<DeliveryMethod> deliveryMethods = new HashSet<>();

    @Column(name = "override_email")
    private String overrideEmail;

    @Column(name = "override_ftp_path")
    private String overrideFtpPath;

    @Column(nullable = false)
    private boolean active = true;

    // Constructors
    public ReportSubscription() {}

    public ReportSubscription(Client client, ReportType reportType, Frequency frequency, 
                            ReportFormat format, Set<DeliveryMethod> deliveryMethods) {
        this.client = client;
        this.reportType = reportType;
        this.frequency = frequency;
        this.format = format;
        this.deliveryMethods = deliveryMethods != null ? deliveryMethods : new HashSet<>();
    }

    // Getters and Setters
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

    public Set<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(Set<DeliveryMethod> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    public String getOverrideEmail() {
        return overrideEmail;
    }

    public void setOverrideEmail(String overrideEmail) {
        this.overrideEmail = overrideEmail;
    }

    public String getOverrideFtpPath() {
        return overrideFtpPath;
    }

    public void setOverrideFtpPath(String overrideFtpPath) {
        this.overrideFtpPath = overrideFtpPath;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Helper methods
    public void addDeliveryMethod(DeliveryMethod method) {
        this.deliveryMethods.add(method);
    }

    public void removeDeliveryMethod(DeliveryMethod method) {
        this.deliveryMethods.remove(method);
    }

    @Override
    public String toString() {
        return "ReportSubscription{" +
                "id=" + id +
                ", reportType=" + reportType +
                ", frequency=" + frequency +
                ", format=" + format +
                ", deliveryMethods=" + deliveryMethods +
                ", active=" + active +
                '}';
    }
}

