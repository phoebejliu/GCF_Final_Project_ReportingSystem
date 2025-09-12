package com.phoebe.pbsub.exception;

/**
 * Custom exception: Duplicate subscription
 */
public class DuplicateSubscriptionException extends RuntimeException {
    
    public DuplicateSubscriptionException(String message) {
        super(message);
    }
    
    public DuplicateSubscriptionException(Long clientId, Object reportType) {
        super("Client " + clientId + " already has a subscription for report type: " + reportType);
    }
}
