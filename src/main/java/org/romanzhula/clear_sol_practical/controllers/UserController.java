package org.romanzhula.clear_sol_practical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.romanzhula.clear_sol_practical.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserRepository userRepository;

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

    @PatchMapping("/update-field/{userId}")
    public ResponseEntity<String> updateUserField(
            @PathVariable Long userId,
            @RequestBody @Valid UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            logger.error("Validation error! Check your input update data.");

            return ResponseEntity.badRequest().body("Validation error! Check your input update data.");
        }

        userService.updateUserByField(userId, userDTO);

        return ResponseEntity.ok("User's field(s) updated successfully");

    }

    @ModelAttribute("userDTO")
    public UserDTO userDTO() {
        return new UserDTO();
    }

}
