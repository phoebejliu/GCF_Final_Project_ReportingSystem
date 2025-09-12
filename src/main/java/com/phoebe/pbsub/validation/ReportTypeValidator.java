package com.phoebe.pbsub.validation;

import com.phoebe.pbsub.entity.enums.ReportType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for ReportType enum
 */
public class ReportTypeValidator implements ConstraintValidator<ValidReportType, String> {
    
    @Override
    public void initialize(ValidReportType constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        
        try {
            ReportType.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
