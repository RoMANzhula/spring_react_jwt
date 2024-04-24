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

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${app.ageRestriction}")
    private int ageRestriction;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Transactional
    public void registerNewUser(UserDTO userDTO) {
        String userFullName = userDTO.getFirstName() + " " + userDTO.getLastName();
        User userFromDB = userRepository.findByEmail(userDTO.getEmail());
        if (validAgeRestriction(userDTO.getBirthDate())) {
            User user = new User();

            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setBirthDate(userDTO.getBirthDate());
            user.setAddress(userDTO.getAddress());
            user.setPhoneNumber(userDTO.getPhoneNumber());

            userRepository.save(user);

        } else if (userFromDB != null) {
            logger.error("Sorry, user {} cannot be register. User with this email already exists!", userFullName);
        } else {
            logger.error("Sorry, user {} cannot be register. User too young!", userFullName);
        }
    }

    private boolean validAgeRestriction(LocalDate userBirthday) {
        var currentDate = LocalDate.now();
        var currentUserAge = Period.between(userBirthday, currentDate).getYears();

        return currentUserAge >= ageRestriction;
    }
}
