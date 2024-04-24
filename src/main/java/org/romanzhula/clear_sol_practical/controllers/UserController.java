package org.romanzhula.clear_sol_practical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> createUser(
            @ModelAttribute("userDTO")
            @Valid UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            logger.error("Validation error! Check your input data.");

            return ResponseEntity.badRequest().body("Validation error! Check your input data.");
        }

        userService.registerNewUser(userDTO);

        return ResponseEntity.ok("User created successfully");
    }

    @ModelAttribute("userDTO")
    public UserDTO userDTO() {
        return new UserDTO();
    }

}
