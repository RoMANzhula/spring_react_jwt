package org.romanzhula.clear_sol_practical.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class ValidAgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    @Value("${app.ageRestriction}")
    private int ageRestriction;

    @Override
    public void initialize(ValidAge constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return false;
        }

        Period period = Period.between(birthDate, LocalDate.now());
        
        return period.getYears() >= ageRestriction;
    }
}

