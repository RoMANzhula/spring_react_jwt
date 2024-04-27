package org.romanzhula.clear_sol_practical.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;

@RequiredArgsConstructor
public class UserEmailUniqueValidator implements ConstraintValidator<UserEmailUnique, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findByEmail(value).isEmpty();
    }

}
