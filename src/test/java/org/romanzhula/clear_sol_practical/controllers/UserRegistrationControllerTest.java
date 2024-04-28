package org.romanzhula.clear_sol_practical.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.controllers.json_responses.CreateUserResponse;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.romanzhula.clear_sol_practical.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRegistrationControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserRegistrationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests for create user method
     * These cases showing work testing without BindingResult
    */
    @Test
    public void testCreateUser_SuccessWithEmptyPhoneNumber() {
        // Given
        var userDTO = UserDTO.builder()
                .id(1L)
                .email("test@test.test")
                .firstName("Name")
                .lastName("Surname")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("123 Main St")
                .phoneNumber("")
                .build()
        ;

        // When
        ResponseEntity<CreateUserResponse> response = userController.createUser(userDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User created successfully", response.getBody().getMessage());
        verify(userService, times(1)).registerNewUser(userDTO);
    }

    @Test
    public void testCreateUser_SuccessWithAllFields() {
        // Given
        var userDTO = UserDTO.builder()
                .id(2L)
                .email("test@test.test")
                .firstName("Name")
                .lastName("Surname")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("123 Main St")
                .phoneNumber("1234567890")
                .build()
        ;

        // When
        ResponseEntity<CreateUserResponse> response = userController.createUser(userDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User created successfully", response.getBody().getMessage());
        verify(userService, times(1)).registerNewUser(userDTO);
    }

    @Test
    public void testCreateUser_InvalidDataEmptyFirstName() {
        // Given
        var userDTO = UserDTO.builder()
                .id(3L)
                .email("test@test.test")
                .firstName("")
                .lastName("Surname")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("123 Main St")
                .phoneNumber("1234567890")
                .build()
        ;

        // When
        ResponseEntity<CreateUserResponse> response = userController.createUser(userDTO);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verifyNoInteractions(userService);
    }

}


    /**
     * Tests for create user method
     * These cases showing work testing with BindingResult in UserController registration method
     */
//    @Test
//    public void testCreateUser_SuccessWithNotAllFields() {
//        // Given
//        var userDTO = UserDTO.builder()
//                .email("test@test.test")
//                .firstName("Name")
//                .lastName("Surname")
//                .birthDate(LocalDate.of(2000, 10, 29))
//                .address("123 Main St")
//                .build()
//        ;
//
//        // When
//        ResponseEntity<RegistrationResponse> response =
//                userController.createUser(userDTO);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Validation error! Check your input data.", response.getBody());
//        verifyNoInteractions(userService);
//    }
//
//    @Test
//    public void testCreateUser_SuccessWithAllFields() {
//        // Given
//        var userDTO = UserDTO.builder()
//                .id(1L)
//                .email("test@test.test")
//                .firstName("Name")
//                .lastName("Surname")
//                .birthDate(LocalDate.of(2000, 10, 29))
//                .address("123 Main St")
//                .phoneNumber("1234567890")
//                .build()
//        ;
//
//        doNothing().when(userService).registerNewUser(userDTO);
//
//        // When
//        ResponseEntity<RegistrationResponse> response =
//                userController.createUser(userDTO);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User created successfully", response.getBody());
//        verify(userService, times(1)).registerNewUser(userDTO);
//    }
//
//    @Test
//    public void testCreateUser_InvalidData() {
//        // Arrange
//        UserDTO userDTO = new UserDTO(); // Empty userDTO
//
//        // When
//        ResponseEntity<RegistrationResponse> response =
//                userController.createUser(userDTO);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verifyNoInteractions(userService);
//    }
//
//    @Test
//    public void testCreateUser_UserAlreadyExists() {
//        // Arrange
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("existing@example.com");
//
//        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));
//
//        // When
//        ResponseEntity<RegistrationResponse> response =
//                userController.createUser(userDTO);
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verifyNoInteractions(userService);
//    }
//
//    @Test
//    public void testCreateUser_NullUserDTO() {
//        // When
//        ResponseEntity<RegistrationResponse> response =
//                userController.createUser(null);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verifyNoInteractions(userService);
//    }
