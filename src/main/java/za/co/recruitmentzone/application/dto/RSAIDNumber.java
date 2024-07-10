package za.co.recruitmentzone.application.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//@Documented
@Constraint(validatedBy = IDNumberValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RSAIDNumber {
    String message() default "Invalid RSA ID number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
