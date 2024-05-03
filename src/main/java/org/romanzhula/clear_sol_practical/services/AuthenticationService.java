package org.romanzhula.clear_sol_practical.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.configurations.jwt.services.JwtService;
import org.romanzhula.clear_sol_practical.controllers.json_requests.LoginStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_requests.RegistrationStaffRequest;
import org.romanzhula.clear_sol_practical.controllers.json_responses.AuthResponse;
import org.romanzhula.clear_sol_practical.dto.StaffDTO;
import org.romanzhula.clear_sol_practical.mappers.StaffMapper;
import org.romanzhula.clear_sol_practical.models.Role;
import org.romanzhula.clear_sol_practical.models.Staff;
import org.romanzhula.clear_sol_practical.models.Token;
import org.romanzhula.clear_sol_practical.models.enums.EnumRole;
import org.romanzhula.clear_sol_practical.repositories.RoleRepository;
import org.romanzhula.clear_sol_practical.repositories.StaffRepository;
import org.romanzhula.clear_sol_practical.repositories.TokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public AuthResponse registrationStaffMember(
            RegistrationStaffRequest request
    ) {

        var newStaffDtoMember = StaffDTO.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build()
        ;

        Staff newStaffMember = StaffMapper.INSTANCE.toStaffModel(newStaffDtoMember);

        if (newStaffMember.getRoles() == null) {
            newStaffMember.setRoles(new HashSet<>());
        }

        var userRole = Role.builder()
                .name(EnumRole.ROLE_USER)
                .build()
        ;

        roleRepository.save(userRole);

        newStaffMember.getRoles().add(userRole);

        staffRepository.save(newStaffMember);

        var jwtToken = jwtService.generateToken(newStaffMember);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build()
        ;
    }

    @Transactional
    public AuthResponse loginStaffMember(LoginStaffRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Staff newStaffMember = staffRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(newStaffMember);

        revokeAllTokenByStaffMember(newStaffMember);
        saveStaffMemberToken(jwtToken, newStaffMember);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .user(newStaffMember)
                .build()
        ;
    }

    private void revokeAllTokenByStaffMember(
            Staff staffMember
    ) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(staffMember.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveStaffMemberToken(
            String jwt,
            Staff staffMember
    ) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setStaffMember(staffMember);
        tokenRepository.save(token);
    }

}
