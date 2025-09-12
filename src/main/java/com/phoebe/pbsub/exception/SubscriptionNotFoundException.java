package com.phoebe.pbsub.exception;

/**
 * Custom exception: Subscription not found
 */
public class SubscriptionNotFoundException extends RuntimeException {
    
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
    
    public SubscriptionNotFoundException(Long id) {
        super("Subscription not found with ID: " + id);
    }
}
