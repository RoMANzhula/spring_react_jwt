package org.romanzhula.clear_sol_practical.controllers.json_requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.romanzhula.clear_sol_practical.models.Role;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationStaffRequest {

    private String email;

    private String username;

    private String phoneNumber;

    private String password;

    private Set<Role> roles;

}
