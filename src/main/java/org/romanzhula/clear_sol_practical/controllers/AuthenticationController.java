package org.romanzhula.clear_sol_practical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.controllers.json_requests.LoginStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_requests.RegistrationStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_responses.AuthResponse;
import org.romanzhula.clear_sol_practical.services.AuthenticationService;
import org.romanzhula.clear_sol_practical.utils.UtilsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(
            @Valid
            @RequestBody RegistrationStaffRequest registrationRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation error! Check your input registration data.");

            Map<String, String> errors = UtilsController.getBindingErrors(bindingResult);

            return ResponseEntity
                    .badRequest()
                    .body(errors)
            ;
        }

        AuthResponse response = authenticationService.registrationStaffMember(registrationRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody LoginStaffRequest loginRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation error! Check your input login data.");

            Map<String, String> errors = UtilsController.getBindingErrors(bindingResult);

            return ResponseEntity
                    .badRequest()
                    .body(errors)
            ;
        }

        AuthResponse response = authenticationService.loginStaffMember(loginRequest);

        return ResponseEntity.ok(response);
    }
}
