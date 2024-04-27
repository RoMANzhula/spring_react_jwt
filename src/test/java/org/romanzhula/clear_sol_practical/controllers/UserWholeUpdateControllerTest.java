package org.romanzhula.clear_sol_practical.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.romanzhula.clear_sol_practical.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class UserWholeUpdateControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Sql(value = {"/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateWholeUser_Success() throws Exception {
        // Given
        Long userId = 1L;
        var userDTO = UserDTO.builder()
                .email("test123123@test.test")
                .firstName("One")
                .lastName("One")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        var content = objectMapper.writeValueAsString(userDTO);

        //When
        var mockResponse = mockMvc
                .perform(put("/api/users/whole-update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andReturn()
                .getResponse()
        ;

        //Then
        assertEquals(HttpStatus.OK.value(), mockResponse.getStatus());
        assertTrue(mockResponse.getContentAsString()
                .contains("User with id: 1 was updated whole successfully!"))
        ;

    }

    @Test
    public void testUpdateWholeUser_NotFoundById() throws Exception {
        // Given
        Long userId = 1L;
        var userDTO = UserDTO.builder()
                .email("test123123@test.test")
                .firstName("One")
                .lastName("One")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        var content = objectMapper.writeValueAsString(userDTO);

        //When
        var mockResponse = mockMvc
                .perform(put("/api/users/whole-update/{userId}", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content)
                        )
                .andReturn()
                .getResponse()
        ;

        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), mockResponse.getStatus());
        assertTrue(mockResponse.getContentAsString()
                .contains("Sorry, user cannot be update whole. User with id 1 NOT FOUND!"))
        ;

    }

    @Test
    public void testUpdateWholeUser_ValidationErrorsMessage() throws Exception {
        // Given
        Long userId = 1L;
        var userDTO = UserDTO.builder()
                .email("test123123@test.test")
                .firstName("")
                .lastName("One")
                .birthDate(LocalDate.of(2000, 10, 29))
                .address("1223 Main St")
                .phoneNumber("12345678901")
                .build()
        ;

        var content = objectMapper.writeValueAsString(userDTO);

        //When
        var mockResponse = mockMvc
                .perform(put("/api/users/whole-update/{userId}", userId)
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
