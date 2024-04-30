package org.romanzhula.clear_sol_practical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {

    private Long id;

    private String email;

    private String username;

    private String phoneNumber;

    private String password;

}
