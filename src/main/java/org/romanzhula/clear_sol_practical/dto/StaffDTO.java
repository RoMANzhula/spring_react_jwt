package org.romanzhula.clear_sol_practical.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.romanzhula.clear_sol_practical.models.Role;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {

    @Column(name = "staff_id")
    private Long id;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = "Email cannot be empty!")
    @Email
    private String email;

    @Column(name = "user_name", nullable = false)
    @NotEmpty(message = "Username cannot be empty!")
    @Length(max = 255, message = "Message too long (limit - 255 B)")
    private String username;

    @Column(name = "phone_number", nullable = false)
    @NotEmpty(message = "Phone number cannot be empty!")
    @Length(min = 9, max = 15, message = "Number is short/long (limit - from 9 to 15 characters)")
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password cannot be empty!")
    @Length(max = 255, message = "Message too long (limit - 255 B)")
    private String password;

}
