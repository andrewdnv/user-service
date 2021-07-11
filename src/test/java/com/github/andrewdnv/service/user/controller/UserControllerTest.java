package com.github.andrewdnv.service.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andrewdnv.service.user.MockObjects;
import com.github.andrewdnv.service.user.exception.NotFoundException;
import com.github.andrewdnv.service.user.exception.UnexpectedException;
import com.github.andrewdnv.service.user.model.User;
import com.github.andrewdnv.service.user.service.api.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("unitTest")
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetUser() throws Exception {
        when(userService.getUser(any())).thenReturn(MockObjects.user());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value("1"))
            .andExpect(jsonPath("$.firstName").value("firstName"))
            .andExpect(jsonPath("$.patronymic").value("patronymic"))
            .andExpect(jsonPath("$.lastName").value("lastName"))
            .andExpect(jsonPath("$.birthDate").value("1990-01-24"))
            .andExpect(jsonPath("$.registrationDate").value("2020-01-24"))
            .andExpect(jsonPath("$.city").value("city"))
            .andExpect(jsonPath("$.mobilePhone").value("+71001001010"))
            .andExpect(jsonPath("$.email").value("user@mail.com"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(userService, times(1)).getUser(eq(1L));
    }

    @Test
    public void testGetUser_NotFound() throws Exception {
        when(userService.getUser(any())).thenThrow(new NotFoundException("message"));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string(""));

        verify(userService, times(1)).getUser(eq(1L));
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(any())).thenReturn(MockObjects.user());

        User user = MockObjects.user().setUserId(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value("1"))
            .andExpect(jsonPath("$.firstName").value("firstName"))
            .andExpect(jsonPath("$.patronymic").value("patronymic"))
            .andExpect(jsonPath("$.lastName").value("lastName"))
            .andExpect(jsonPath("$.birthDate").value("1990-01-24"))
            .andExpect(jsonPath("$.registrationDate").value("2020-01-24"))
            .andExpect(jsonPath("$.city").value("city"))
            .andExpect(jsonPath("$.mobilePhone").value("+71001001010"))
            .andExpect(jsonPath("$.email").value("user@mail.com"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(userService, times(1)).createUser(eq(user));
    }

    @Test
    public void testCreateUser_Unexpected() throws Exception {
        when(userService.createUser(any())).thenThrow(new UnexpectedException("message"));

        User user = MockObjects.user().setUserId(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string(""));

        verify(userService, times(1)).createUser(eq(user));
    }

    @Test
    public void testCreateUser_MissingFirstName() throws Exception {
        User user = MockObjects.user().setFirstName(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_IncorrectFirstName() throws Exception {
        User user = MockObjects.user().setFirstName("");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_IncorrectPatronymic() throws Exception {
        User user = MockObjects.user().setPatronymic("");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_MissingLastName() throws Exception {
        User user = MockObjects.user().setLastName(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_IncorrectLastName() throws Exception {
        User user = MockObjects.user().setLastName("");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_MissingRegistrationDate() throws Exception {
        User user = MockObjects.user().setRegistrationDate(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_IncorrectCity() throws Exception {
        User user = MockObjects.user().setCity("");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_IncorrectMobilePhone() throws Exception {
        User user = MockObjects.user().setMobilePhone("");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_IncorrectEmail() throws Exception {
        User user = MockObjects.user().setEmail("");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testCreateUser_MissingStatus() throws Exception {
        User user = MockObjects.user().setStatus(null);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(any())).thenReturn(MockObjects.user());

        User user = MockObjects.user();

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/users/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value("1"))
            .andExpect(jsonPath("$.firstName").value("firstName"))
            .andExpect(jsonPath("$.patronymic").value("patronymic"))
            .andExpect(jsonPath("$.lastName").value("lastName"))
            .andExpect(jsonPath("$.birthDate").value("1990-01-24"))
            .andExpect(jsonPath("$.registrationDate").value("2020-01-24"))
            .andExpect(jsonPath("$.city").value("city"))
            .andExpect(jsonPath("$.mobilePhone").value("+71001001010"))
            .andExpect(jsonPath("$.email").value("user@mail.com"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(userService, times(1)).updateUser(eq(user));
    }

    @Test
    public void testUpdateUser_Unexpected() throws Exception {
        when(userService.updateUser(any())).thenThrow(new UnexpectedException("message"));

        User user = MockObjects.user();

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/users/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string(""));

        verify(userService, times(1)).updateUser(eq(user));
    }

    @Test
    public void testUpdateUser_MissingUserId() throws Exception {
        User user = MockObjects.user().setUserId(null);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/users/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testUpdateUser_IncorrectUserId() throws Exception {
        User user = MockObjects.user().setUserId(0L);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/users/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));

        verify(userService, never()).createUser(eq(user));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/1"))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));

        verify(userService, times(1)).deleteUser(eq(1L));
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        doThrow(new NotFoundException("message")).when(userService).deleteUser(any());

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string(""));

        verify(userService, times(1)).deleteUser(eq(1L));
    }

}