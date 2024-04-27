package org.romanzhula.clear_sol_practical.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.romanzhula.clear_sol_practical.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail_ExistingEmail_ShouldReturnUser() {
        // Given
        String email = "testemail1@test.test";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail(email);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    public void testFindByEmail_NonExistingEmail_ShouldReturnEmptyOptional() {
        // Given
        String email = "nonexisting@test.test";

        // When
        Optional<User> foundUser = userRepository.findByEmail(email);

        // Then
        assertTrue(foundUser.isEmpty());
    }

    @Test
    public void testFindByBirthDateBetween_ShouldReturnUsers() {
        // Given
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(1995, 12, 31);
        User user1 = new User();
        user1.setBirthDate(LocalDate.of(1992, 5, 15));
        userRepository.save(user1);
        User user2 = new User();
        user2.setBirthDate(LocalDate.of(1994, 8, 20));
        userRepository.save(user2);

        // When
        List<User> users = userRepository.findByBirthDateBetween(fromDate, toDate);

        // Then
        assertEquals(2, users.size());
    }

    @Test
    public void testFindByBirthDateBetween_NoUsersFound_ShouldReturnEmptyList() {
        // Given
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(1995, 12, 31);

        // When
        List<User> users = userRepository.findByBirthDateBetween(fromDate, toDate);

        // Then
        assertTrue(users.isEmpty());
    }
}
