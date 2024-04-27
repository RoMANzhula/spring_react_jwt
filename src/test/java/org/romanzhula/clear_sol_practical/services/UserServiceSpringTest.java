package org.romanzhula.clear_sol_practical.services;

import org.junit.jupiter.api.Test;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.models.User;
import org.romanzhula.clear_sol_practical.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class UserServiceSpringTest {

    @Autowired
    private UserService userService;


    @Test
    public void testRegisterNewUser_UnderageUser() {
        // Given
        LocalDate underageBirthDate = LocalDate.now().minusYears(17);
        UserDTO userDTO = UserDTO.builder()
                .email("testemail1@test.test")
                .firstName("One")
                .lastName("One")
                .birthDate(underageBirthDate)
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                        userService.registerNewUser(userDTO), "User under 18 should not be registered"
                    )
        ;
    }

    @Test
    public void testUpdateWholeUser_Success() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = UserDTO.builder()
                .email("updated@test.test")
                .firstName("One")
                .lastName("One")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        User user = new User();
        user.setId(userId);
        user.setEmail(userDTO.getEmail());

        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));

        UserService userService = new UserService(userRepositoryMock);

        // When
        userService.updateWholeUser(userDTO, userId);

        // Then
        assertEquals(userDTO.getEmail(), user.getEmail());
    }

}

