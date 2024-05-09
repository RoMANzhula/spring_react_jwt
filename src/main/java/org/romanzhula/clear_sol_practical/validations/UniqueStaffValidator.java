package org.romanzhula.clear_sol_practical.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.repositories.StaffRepository;

@RequiredArgsConstructor
public class UniqueStaffValidator implements ConstraintValidator<UniqueStaff, String> {

    private final StaffRepository staffRepository;

    private String fieldName;

    @Override
    public void initialize(UniqueStaff constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // without empty fields
        }

        switch (fieldName) {
            case "email":
                return staffRepository.findByEmail(value).isEmpty();
            case "phoneNumber":
                return staffRepository.findByPhoneNumber(value).isEmpty();
            case "username":
                return staffRepository.findByUsername(value).isEmpty();
            default:
                return false; // for unknown field
        }
    }

}

