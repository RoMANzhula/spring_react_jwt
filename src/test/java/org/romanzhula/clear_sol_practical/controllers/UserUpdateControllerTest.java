package org.romanzhula.clear_sol_practical.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.romanzhula.clear_sol_practical.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class UserUpdateControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserUpdateControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNotFullUpdateUserField_Success() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = UserDTO.builder()
                .email("test123123@test.test")
                .firstName("One")
                .lastName("One")
                .build()
        ;

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        ResponseEntity<?> response = userController.updateUserField(userId, userDTO, bindingResult);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's field(s) updated successfully", response.getBody());
    }

    @Test
    public void testFullUpdateUserField_Success() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = UserDTO.builder()
                .email("test123123@test.test")
                .firstName("One")
                .lastName("One")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        ResponseEntity<?> response = userController.updateUserField(userId, userDTO, bindingResult);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's field(s) updated successfully", response.getBody());
    }

    @Test
    public void testUpdateUserField_ValidationErrors() {
        // Given
        Long userId = 2L;
        UserDTO userDTO = UserDTO.builder()
                .email("invalid-email") // Invalid email format
                .build()
        ;

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<?> response = userController.updateUserField(userId, userDTO, bindingResult);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals(0, errors.size());
        verifyNoInteractions(userService);
    }

    @Test
    public void testUpdateUserField_ValidationErrorsMessage() throws Exception {
        // Given
        Long userId = 1L;
        var userDTO = UserDTO.builder()
                .firstName("")
                .email("test-test.test")
                .build()
        ;

        var content = objectMapper.writeValueAsString(userDTO);

        //When
        var mockResponse = mockMvc
                .perform(patch("/api/users/update-field/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                )
                .andReturn()
                .getResponse()
        ;

        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), mockResponse.getStatus());
        assertTrue(mockResponse.getContentAsString().contains("User first name cannot be empty!"));

    }

}
