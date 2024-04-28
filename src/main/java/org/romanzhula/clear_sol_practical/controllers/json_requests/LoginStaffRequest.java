package org.romanzhula.clear_sol_practical.controllers.json_requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginStaffRequest {

    private String username;

    private String password;

}
