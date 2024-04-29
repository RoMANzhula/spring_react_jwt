package org.romanzhula.clear_sol_practical.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.configurations.jwt.services.JwtService;
import org.romanzhula.clear_sol_practical.controllers.json_requests.LoginStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_requests.RegistrationStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_responses.AuthResponse;
import org.romanzhula.clear_sol_practical.models.Role;
import org.romanzhula.clear_sol_practical.models.Staff;
import org.romanzhula.clear_sol_practical.models.enums.EnumRole;
import org.romanzhula.clear_sol_practical.repositories.StaffRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrationStaffMember(RegistrationStaffRequest request) {



        var newStaffMember = Staff.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build()
        ;

        var userRole = Role.builder()
                .name(EnumRole.ROLE_USER)
                .staffMember(newStaffMember)
                .build()
        ;

        newStaffMember.setRoles(new HashSet<>(List.of(userRole)));

        staffRepository.save(newStaffMember);

        var jwtToken = jwtService.generateToken(newStaffMember);

        return AuthResponse.builder()
                .token(jwtToken)
                .build()
        ;
    }

    public AuthResponse loginStaffMember(LoginStaffRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var loginStaffMember = staffRepository.findByUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(loginStaffMember.orElseThrow());

        return AuthResponse.builder()
                .token(jwtToken)
                .build()
        ;
    }
}
