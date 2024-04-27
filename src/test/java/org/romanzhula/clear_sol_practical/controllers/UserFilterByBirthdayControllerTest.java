package org.romanzhula.clear_sol_practical.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.romanzhula.clear_sol_practical.models.User;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserFilterByBirthdayControllerTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    public UserFilterByBirthdayControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsersByBirthdayRange_Success() {
        // Given
        LocalDate from = LocalDate.of(2000, 1, 1);
        LocalDate to = LocalDate.of(2000, 12, 31);
        List<User> users = new ArrayList<>();
        when(userRepository.findByBirthDateBetween(from, to)).thenReturn(users);

        // When
        ResponseEntity<?> response = userController.getUsersByBirthdayRange(from, to);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userRepository, times(1)).findByBirthDateBetween(from, to);
    }

    @Test
    public void testGetUsersByBirthdayRange_InvalidDateRange() {
        // Given
        LocalDate from = LocalDate.of(2001, 1, 1);
        LocalDate to = LocalDate.of(2000, 12, 31);

        // When
        ResponseEntity<?> response = userController.getUsersByBirthdayRange(from, to);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Date From should be less than date To.", response.getBody());
        verifyNoInteractions(userRepository);
    }
}
