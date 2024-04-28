package org.romanzhula.clear_sol_practical.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.controllers.json_requests.LoginStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_requests.RegistrationStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_responses.AuthResponse;
import org.romanzhula.clear_sol_practical.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> registration(
            @RequestBody RegistrationStaffRequest registrationRequest
    ) {
        return ResponseEntity.ok(authenticationService.registrationStaffMember(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginStaffRequest loginRequest
    ) {
        return ResponseEntity.ok(authenticationService.loginStaffMember(loginRequest));
    }
}
