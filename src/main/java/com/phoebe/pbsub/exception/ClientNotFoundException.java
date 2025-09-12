package com.phoebe.pbsub.exception;

/**
 * Custom exception: Client not found
 */
public class ClientNotFoundException extends RuntimeException {
    
    public ClientNotFoundException(String message) {
        super(message);
    }
    
    public ClientNotFoundException(Long id) {
        super("Client not found with ID: " + id);
    }
}
