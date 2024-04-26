package org.romanzhula.clear_sol_practical.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserEmailUniqueValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserEmailUnique {
    String message() default "User with this email existed yet!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
