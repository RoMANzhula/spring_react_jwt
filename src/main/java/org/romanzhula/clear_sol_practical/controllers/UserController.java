package org.romanzhula.clear_sol_practical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.controllers.json_responses.CreateUserResponse;
import org.romanzhula.clear_sol_practical.models.User;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.romanzhula.clear_sol_practical.services.UserService;
import org.romanzhula.clear_sol_practical.utils.UtilsController;
import org.romanzhula.clear_sol_practical.validations.UserCreateMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
//@CrossOrigin
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * Example with validation marker
     * with exception handler to BadRequest
     * without BindingResult
    */
    @Validated({UserCreateMarker.class})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid
            @RequestBody UserDTO userDTO
    ) {
        if (userDTO.getFirstName().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .build()
            ;
        }

        userService.registerNewUser(userDTO);

        return ResponseEntity
                .ok()
                .body(new CreateUserResponse("User created successfully"))
        ;
    }

    /**
     * Example with validation marker
     * with exception handler to BadRequest
     * without BindingResult
     */
//    @PostMapping("/registration")
//    public ResponseEntity<?> createUser(
////            @ModelAttribute("userDTO")
//            @Valid UserDTO userDTO,
//            BindingResult bindingResult
//    ) {
//        if (!bindingResult.hasErrors()) {
//            logger.error("Validation error! Check your input data.");
//
//            Map<String, String> errors = UtilsController.getBindingErrors(bindingResult);
//
//            return ResponseEntity
//                    .badRequest()
//                    .body(errors)
//            ;
//        }
//
//        userService.registerNewUser(userDTO);
//
//        return ResponseEntity.ok("User created successfully");
//    }

    /**
     * Example with BindingResult
     * with wrapping binding errors to string VS field
     */
    @PatchMapping("/update-field/{userId}")
    public ResponseEntity<?> updateUserField(
            @PathVariable Long userId,
            @Valid
            @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation error! Check your input update data.");

            Map<String, String> errors = UtilsController.getBindingErrors(bindingResult);

            return ResponseEntity
                    .badRequest()
                    .body(errors)
            ;
        }

        userService.updateUserByField(userId, userDTO);

        return ResponseEntity
                .ok()
                .body("User's field(s) updated successfully")
        ;

    }

    @PutMapping("/whole-update/{userId}")
    public ResponseEntity<?> updateWholeUser(
        @PathVariable Long userId,
        @Valid
        @RequestBody UserDTO userDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation error! Check all input fields data.");

            Map<String, String> errors = UtilsController.getBindingErrors(bindingResult);

            return ResponseEntity
                    .badRequest()
                    .body(errors)
            ;
        }

        userService.updateWholeUser(userDTO, userId);

        return ResponseEntity
                .ok()
                .body("User with id: " + userId + " was updated whole successfully!")
        ;
    }

    @DeleteMapping("/remove-user/{userId}")
    public ResponseEntity<?> removeUserById(
            @PathVariable Long userId
    ) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);

            return ResponseEntity
                    .ok()
                    .body("User deleted successfully!")
            ;
        }

        logger.error("ERROR: User with id {} NOT FOUND!", userId);
        return ResponseEntity
                .notFound()
                .build()
        ;
    }

    @GetMapping("/birthday-range")
    public ResponseEntity<?> getUsersByBirthdayRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate to
    ) {
        if (from.isAfter(to)) {
            return ResponseEntity
                    .badRequest()
                    .body("Date From should be less than date To.")
            ;
        }

        List<User> users = userRepository.findByBirthDateBetween(from, to);
        return ResponseEntity
                .ok()
                .body(users)
        ;
    }

//    @ModelAttribute("userDTO")
//    public UserDTO userDTO() {
//        return new UserDTO();
//    }

}
