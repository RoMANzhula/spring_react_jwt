package org.romanzhula.clear_sol_practical.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.models.User;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.slf4j.Logger;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterNewUser_Success() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .email("test@test.test")
                .firstName("NameOne")
                .lastName("SurnameOne")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .phoneNumber("1234567890")
                .build()
        ;

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        // When
        userService.registerNewUser(userDTO);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserByField_Success() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = UserDTO.builder()
                .email("updated@test.test")
                .build()
        ;

        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.updateUserByField(userId, userDTO);

        // Then
        assertEquals(userDTO.getEmail(), user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateWholeUser_UserNotFound() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .email("updated@test.test")
                .firstName("One")
                .lastName("One")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        Long userId = 222L;

        // Then
        assertThrows(EntityNotFoundException.class, () -> {
                            userService.updateWholeUser(userDTO, userId);
        });
    }

    @Test
    public void testRegisterNewUser_EmailAlreadyExists() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .email("testemail1@test.test")
                .firstName("One")
                .lastName("One")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        when(userRepository.findByEmail("testemail1@test.test")).thenReturn(Optional.of(new User()));

        // When & Then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        userService.registerNewUser(userDTO)
                )
        ;

        String expectedMessage = "User with this email already exists!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRegisterNewUser_NullUserDTO() {
        // When & Then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        userService.registerNewUser(null)
                )
        ;

        String expectedMessage = "UserDTO is null. Unable to register user.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateUserByField_UserNotFound() {
        // Given
        Long userId = 2L;
        UserDTO userDTO = UserDTO.builder()
                .email("updated@test.test")
                .build()
        ;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () ->
                userService.updateUserByField(userId, userDTO)
        );
    }

}