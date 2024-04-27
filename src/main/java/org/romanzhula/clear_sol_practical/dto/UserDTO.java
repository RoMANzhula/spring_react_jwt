package org.romanzhula.clear_sol_practical.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.romanzhula.clear_sol_practical.validations.UserCreateMarker;
import org.romanzhula.clear_sol_practical.validations.UserEmailUnique;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = "Email cannot be empty!")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format. Only letters, numbers, dots, and one @ symbol.")
    @Email
    @UserEmailUnique(groups = UserCreateMarker.class)
    private String email;

    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "User first name cannot be empty!")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Invalid characters in first name. Only letters are allowed.")
    @Length(max = 255, message = "Message too long (limit - 255 B)")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotEmpty(message = "User last name cannot be empty!")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Invalid characters in last name. Only letters are allowed.")
    @Length(max = 255, message = "Message too long (limit - 255 B)")
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @Temporal(value = TemporalType.DATE)
    @Past(message = "Only past data!")
    private LocalDate birthDate;

    @Length(max = 255, message = "Message too long (limit - 255 B)")
    private String address;

    @Length(min = 9, max = 15, message = "Number is short/long (limit - from 9 to 15 characters)")
    private String phoneNumber;
}
