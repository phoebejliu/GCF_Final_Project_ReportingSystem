package com.phoebe.pbsub.dto;

import com.phoebe.pbsub.entity.enums.DeliveryMethod;
import com.phoebe.pbsub.entity.enums.Frequency;
import com.phoebe.pbsub.entity.enums.ReportFormat;
import com.phoebe.pbsub.validation.ValidReportType;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for ReportSubscription API requests
 */
public class ReportSubscriptionRequest {
    
    @NotNull(message = "Client ID cannot be null")
    private Long clientId;
    
    @NotNull(message = "Report type cannot be null")
    @ValidReportType
    private String reportType;
    
    @NotNull(message = "Frequency cannot be null")
    private Frequency frequency;
    
    @NotNull(message = "Format cannot be null")
    private ReportFormat format;
    
    @NotNull(message = "Delivery method cannot be null")
    private DeliveryMethod deliveryMethod;
    
    // Constructors
    public ReportSubscriptionRequest() {}
    
    public ReportSubscriptionRequest(Long clientId, String reportType, Frequency frequency, 
                                   ReportFormat format, DeliveryMethod deliveryMethod) {
        this.clientId = clientId;
        this.reportType = reportType;
        this.frequency = frequency;
        this.format = format;
        this.deliveryMethod = deliveryMethod;
    }
    
    // Getters and Setters
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
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
}
