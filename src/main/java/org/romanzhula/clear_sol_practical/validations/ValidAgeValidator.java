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
            return false; // Якщо дата народження не вказана, то вона не валідна
        }
        // Обчислюємо різницю віку між датою народження і поточною датою
        Period period = Period.between(birthDate, LocalDate.now());
        // Перевіряємо, чи користувачу не менше 18 років
        return period.getYears() >= ageRestriction;
    }
}

