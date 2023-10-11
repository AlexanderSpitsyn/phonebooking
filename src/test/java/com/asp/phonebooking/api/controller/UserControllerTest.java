package com.asp.phonebooking.api.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.asp.phonebooking.api.dto.CreateUserDTO;
import com.asp.phonebooking.api.dto.UpdateUserDTO;
import com.asp.phonebooking.api.dto.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * TODO Add more tests (check permissions, validation, cases when a user exists/doesn't exist, etc.)
 */
@ComponentScan("com.asp.phonebooking")
@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String TEST_USERNAME = "testUserName";
    private static final String TEST_PASSWORD = "testPassword";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDetailsManager userDetailsManager;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        when(passwordEncoder.encode(anyString())).then(invocation -> invocation.getArgument(0));
    }

    @Test
    @WithMockUser(roles = "USER")
    void create403() throws Exception {
        var roles = Set.of(UserRole.ROLE_USER, UserRole.ROLE_ADMIN);
        var createUserDTO = new CreateUserDTO(TEST_USERNAME, TEST_PASSWORD, roles);

        mockMvc.perform(post("/user")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create200() throws Exception {
        var roles = Set.of(UserRole.ROLE_USER, UserRole.ROLE_ADMIN);
        var createUserDTO = new CreateUserDTO(TEST_USERNAME, TEST_PASSWORD, roles);

        mockMvc.perform(post("/user")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME))
                .andExpect(jsonPath("$.roles").value(containsInAnyOrder("ROLE_USER", "ROLE_ADMIN")));

        verify(userDetailsManager).createUser(argThat(argument -> {
            var actualRoles = argument.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(UserRole::valueOf)
                    .collect(Collectors.toSet());
            return TEST_USERNAME.equals(argument.getUsername())
                    && TEST_PASSWORD.equals(argument.getPassword())
                    && roles.equals(actualRoles);
        }));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update200() throws Exception {
        when(userDetailsManager.userExists(TEST_USERNAME)).thenReturn(true);
        var role = UserRole.ROLE_ADMIN;
        var createUserDTO = new UpdateUserDTO(TEST_PASSWORD, Set.of(role));

        mockMvc.perform(put("/user/" + TEST_USERNAME)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME))
                .andExpect(jsonPath("$.roles").value(containsInAnyOrder(role.name())));

        verify(userDetailsManager).updateUser(argThat(argument -> {
            var actualRoles = argument.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(UserRole::valueOf)
                    .collect(Collectors.toSet());
            return TEST_USERNAME.equals(argument.getUsername())
                    && Set.of(role).equals(actualRoles);
        }));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete200() throws Exception {
        when(userDetailsManager.userExists(TEST_USERNAME)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + TEST_USERNAME))
                .andExpect(status().isOk());

        verify(userDetailsManager).deleteUser(TEST_USERNAME);
    }
}