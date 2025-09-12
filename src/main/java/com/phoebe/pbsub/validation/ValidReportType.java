package com.phoebe.pbsub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for ReportType enum
 */
@Documented
@Constraint(validatedBy = ReportTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReportType {
    String message() default "Invalid report type. Valid values are: TRADE_CONFIRM, OPTIONS_EXPIRY, MARGIN_CALL, STATEMENT, DAILY_PNL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
