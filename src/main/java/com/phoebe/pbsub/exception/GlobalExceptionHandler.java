package com.phoebe.pbsub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation exceptions
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Object handleValidationException(Exception ex, 
                                          jakarta.servlet.http.HttpServletRequest request, 
                                          Model model) {
        Map<String, String> errors = new HashMap<>();
        
        if (ex instanceof BindException) {
            ((BindException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        
        // If it's an API request, return JSON response
        if (request.getRequestURI().startsWith("/api/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Validation Failed");
            response.put("message", "Please check input parameters");
            response.put("errors", errors);
            return ResponseEntity.badRequest().body(response);
        }
        
        // Otherwise, return Thymeleaf page
        model.addAttribute("error", "Validation Failed");
        model.addAttribute("errors", errors);
        return "error";
    }

    /**
     * Handle ClientNotFoundException
     */
    @ExceptionHandler(ClientNotFoundException.class)
    public Object handleClientNotFound(ClientNotFoundException ex, 
                                      jakarta.servlet.http.HttpServletRequest request, 
                                      Model model) {
        // If it's an API request, return JSON response
        if (request.getRequestURI().startsWith("/api/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Client Not Found");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // Otherwise, return Thymeleaf page
        model.addAttribute("error", "Client Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    /**
     * Handle SubscriptionNotFoundException
     */
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public Object handleSubscriptionNotFound(SubscriptionNotFoundException ex, 
                                            jakarta.servlet.http.HttpServletRequest request, 
                                            Model model) {
        // If it's an API request, return JSON response
        if (request.getRequestURI().startsWith("/api/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Subscription Not Found");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // Otherwise, return Thymeleaf page
        model.addAttribute("error", "Subscription Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    /**
     * Handle DuplicateSubscriptionException
     */
    @ExceptionHandler(DuplicateSubscriptionException.class)
    public Object handleDuplicateSubscription(DuplicateSubscriptionException ex, 
                                             jakarta.servlet.http.HttpServletRequest request, 
                                             Model model) {
        // If it's an API request, return JSON response
        if (request.getRequestURI().startsWith("/api/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Duplicate Subscription");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        // Otherwise, return Thymeleaf page
        model.addAttribute("error", "Duplicate Subscription");
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgument(IllegalArgumentException ex, 
                                       jakarta.servlet.http.HttpServletRequest request, 
                                       Model model) {
        // If it's an API request, return JSON response
        if (request.getRequestURI().startsWith("/api/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid Parameter");
            response.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        // Otherwise, return Thymeleaf page
        model.addAttribute("error", "Invalid Parameter");
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    /**
     * Handle JSON parsing and enum conversion errors
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
                                             jakarta.servlet.http.HttpServletRequest request, 
                                             Model model) {
        String requestURI = request.getRequestURI();
        
        // If it's an API request, return JSON response
        if (requestURI.startsWith("/api/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid JSON Format");
            
            String message = ex.getMessage();
            if (message != null && message.contains("Cannot deserialize value")) {
                response.put("message", "Invalid enum value provided. Please check the valid values for each field.");
            } else {
                response.put("message", "Invalid JSON format. Please check your request body.");
            }
            
            return ResponseEntity.badRequest().body(response);
        }
        // Otherwise, return Thymeleaf page
        model.addAttribute("error", "Invalid Request Format");
        model.addAttribute("message", "Please check your input data");
        return "error";
    }

    /**
     * Handle generic exceptions
     */
    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception ex, 
                                        jakarta.servlet.http.HttpServletRequest request, 
                                        Model model) {
        // Skip handling for Swagger/OpenAPI related requests
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/swagger-ui") || 
            requestURI.startsWith("/v3/api-docs") || 
            requestURI.startsWith("/webjars") ||
            requestURI.startsWith("/swagger-resources")) {
            return null; // Let Spring handle it
        }
        
        // If it's an API request, return JSON response
        if (requestURI.startsWith("/api/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "System Error");
            response.put("message", "An unknown error occurred, please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        // Otherwise, return Thymeleaf page
        model.addAttribute("error", "System Error");
        model.addAttribute("message", "An unknown error occurred, please try again later");
        return "error";
    }
}
