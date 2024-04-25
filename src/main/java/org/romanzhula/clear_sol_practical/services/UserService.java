package org.romanzhula.clear_sol_practical.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.clear_sol_practical.controllers.UserController;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.models.User;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${app.ageRestriction}")
    private int ageRestriction;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Transactional
    public void registerNewUser(
            UserDTO userDTO
    ) {
        if (userDTO != null) {
            String userFullName = userDTO.getFirstName() + " " + userDTO.getLastName();
            if (userDTO.getBirthDate() != null && validAgeRestriction(userDTO.getBirthDate())) {
                User userFromDB = userRepository.findByEmail(userDTO.getEmail());
                if (userFromDB == null) {
                    User user = new User();
                    user.setEmail(userDTO.getEmail());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setBirthDate(userDTO.getBirthDate());
                    user.setAddress(userDTO.getAddress());
                    user.setPhoneNumber(userDTO.getPhoneNumber());
                    userRepository.save(user);
                } else {
                    logger.error(
                            "Sorry, user {} cannot be registered. User with this email already exists!",
                            userFullName
                    );
                }
            } else {
                logger.error(
                        "Sorry, user {} cannot be registered. Invalid birth date or age restriction not met!",
                        userFullName
                );
            }
        } else {
            logger.error("UserDTO is null. Unable to register user.");
        }
    }


    private boolean validAgeRestriction(LocalDate userBirthday) {
        if (userBirthday != null) {
            var currentDate = LocalDate.now();
            var currentUserAge = Period.between(userBirthday, currentDate).getYears();
            return currentUserAge >= ageRestriction;
        }
        return false;
    }

    @Transactional
    public void updateUserByField(
            Long userId,
            UserDTO userDTO
    ) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userDTO.getEmail() != null) {
                user.setEmail(userDTO.getEmail());
            }
            if (userDTO.getFirstName() != null) {
                user.setFirstName(userDTO.getFirstName());
            }
            if (userDTO.getLastName() != null) {
                user.setLastName(userDTO.getLastName());
            }
            if (userDTO.getBirthDate() != null) {
                user.setBirthDate(userDTO.getBirthDate());
            }
            if (userDTO.getAddress() != null) {
                user.setAddress(userDTO.getAddress());
            }
            if (userDTO.getPhoneNumber() != null) {
                user.setPhoneNumber(userDTO.getPhoneNumber());
            }
            userRepository.save(user);
        } else {
            logger.error("Sorry, user cannot be update. User NOT FOUND!");
        }
    }
}
