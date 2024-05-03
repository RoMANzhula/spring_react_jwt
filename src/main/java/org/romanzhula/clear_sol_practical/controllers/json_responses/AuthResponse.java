package org.romanzhula.clear_sol_practical.controllers.json_responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.romanzhula.clear_sol_practical.models.Staff;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;

    private Staff user;

}
