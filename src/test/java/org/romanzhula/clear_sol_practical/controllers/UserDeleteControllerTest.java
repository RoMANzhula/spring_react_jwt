package org.romanzhula.clear_sol_practical.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserDeleteControllerTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    public UserDeleteControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRemoveUserById_Success() {
        // Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        ResponseEntity<?> response = userController.removeUserById(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully!", response.getBody());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testRemoveUserById_UserNotFound() {
        // Given
        Long userId = 2L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // When
        ResponseEntity<?> response = userController.removeUserById(userId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepository, times(1)).existsById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}
