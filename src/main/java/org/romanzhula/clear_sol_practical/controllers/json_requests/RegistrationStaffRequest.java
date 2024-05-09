package org.romanzhula.clear_sol_practical.controllers.json_requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.romanzhula.clear_sol_practical.models.Role;
import org.romanzhula.clear_sol_practical.validations.UniqueStaff;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationStaffRequest {

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty(message = "Email cannot be empty!")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format. Only letters, numbers, dots, and one @ symbol.")
    @Email(message = "Invalid email format")
    @UniqueStaff(message = "Email must be unique", fieldName = "email")
    private String email;

    @Column(name = "user_name", nullable = false, unique = true)
    @NotEmpty(message = "Username cannot be empty!")
    @Length(max = 255, message = "Message too long (limit - 255 B)")
    @UniqueStaff(message = "Username must be unique", fieldName = "username")
    private String username;

    @Column(name = "phone_number", nullable = false, unique = true)
    @NotEmpty(message = "Phone number cannot be empty!")
    @Length(min = 9, max = 15, message = "Number is short/long (limit - from 9 to 15 characters)")
    @Pattern(regexp = "\\+?\\d+", message = "Phone number must contain only digits and may start with a plus sign")
    @UniqueStaff(message = "Phone number must be unique", fieldName = "phoneNumber")
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password cannot be empty!")
    @Length(max = 255, message = "Message too long (limit - 255 B)")
    private String password;

    private Set<Role> roles;

}
